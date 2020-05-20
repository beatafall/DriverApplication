package com.example.terkepes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.terkepes.Class.Line;
import com.example.terkepes.Retrofit.ApiUtils;
import com.example.terkepes.Retrofit.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeachLine extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btn_seatch;
    String selectedLine;
    Spinner spinner;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_line);

        btn_seatch=findViewById(R.id.btn_searchline);
        spinner =findViewById(R.id.spinnerLines);

        userService= ApiUtils.getAPIService();

        userService.getLines().enqueue(new Callback<List<Line>>() {
            @Override
            public void onResponse(Call<List<Line>> call, Response<List<Line>> response) {
                List<Line> lines = response.body();

                List<String> lineString = new ArrayList<String>();

                for (Line l : lines) {
                    lineString.add(l.getLine().toString());
                }

                spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) SeachLine.this);
                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(SeachLine.this, android.R.layout.simple_spinner_dropdown_item, lineString);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedLine=spinner.getSelectedItem().toString();
                        Log.d("d",selectedLine);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getApplicationContext(), "Nem valasztotta ki a vonalat", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Line>> call, Throwable t) {
                Toast.makeText(SeachLine.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        Bundle extras = getIntent().getExtras();
        final String username, userId;

        if(extras != null) {
            username = extras.getString("driverName");
            userId = extras.getString("driverId");

            btn_seatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ViewLine.class);
                    intent.putExtra("line",selectedLine);
                    intent.putExtra("driverId", userId);
                    intent.putExtra("latitude", getIntent().getStringExtra("latitude"));
                    intent.putExtra("longitude", getIntent().getStringExtra("longitude"));
                    intent.putExtra("driverName", username);
                    startActivity(intent);
                }
            });

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
            bottomNavigationView.setSelectedItemId(R.id.searchLine);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case  R.id.newMessage:
                            Intent intent = new Intent(SeachLine.this, ViewMessagesFromAdmin.class);
                            intent.putExtra("driverId", userId);
                            intent.putExtra("latitude", getIntent().getStringExtra("latitude"));
                            intent.putExtra("longitude", getIntent().getStringExtra("longitude"));
                            intent.putExtra("driverName", username);
                            startActivity(intent);
                            break;
                        case R.id.sendMessage:
                            Intent intent2 = new Intent(SeachLine.this, SendNewMessage.class);
                            intent2.putExtra("latitude", getIntent().getStringExtra("latitude"));
                            intent2.putExtra("longitude",getIntent().getStringExtra("longitude"));
                            intent2.putExtra("driverId", userId);
                            intent2.putExtra("driverName", username);
                            startActivity(intent2);
                            break;
                        case R.id.searchLine:
                            break;
                    }
                    return true;
                }
            });

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Ki valasztotta a vonalat", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(), "Nem valasztotta ki a vonalat", Toast.LENGTH_SHORT).show();
    }
}
