package com.example.terkepes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.terkepes.Class.Driver;
import com.example.terkepes.Retrofit.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverLogin extends AppCompatActivity {

    EditText name, pass;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.name);
        pass=findViewById(R.id.pass);
        btn_login=findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UserService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserService userService = retrofit.create(UserService.class);
                Call<List<Driver>> call = userService.getDrivers();
                call.enqueue(new Callback<List<Driver>>() {
                    @Override
                    public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                        List<Driver> drivers = response.body();

                        String username = name.getText().toString();
                        String userpassword = pass.getText().toString();

                        if(validateLogin(username, userpassword)) {
                            for (Driver d : drivers) {
                                Log.d("driverid", d.getDriverId());
                                Log.d("name", d.getDriverName());
                                Log.d("pass", d.getDriverPassword());

                                if (d.getDriverName().equals(username) && d.getDriverPassword().equals(userpassword)) {
                                    Intent intent = new Intent(getApplicationContext(), Map.class);
                                    intent.putExtra("driver", d.getDriverName());
                                    intent.putExtra("id", d.getDriverId());
                                    startActivity(intent);
                                }else if(!d.getDriverName().equals(username) && !d.getDriverPassword().equals(userpassword)){
                                    Toast.makeText(getApplicationContext(), "Helytelen név vagy jelszó!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Driver>> call, Throwable t) {
                        Toast.makeText(DriverLogin.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Név megadása kötelező!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Jelszó megadása kötelező!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



}
