package com.example.terkepes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.terkepes.Class.Messages;
import com.example.terkepes.Class.MessageType;
import com.example.terkepes.Retrofit.ApiUtils;
import com.example.terkepes.Retrofit.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Form extends AppCompatActivity {

    TextView message, line, bus, lat, lon, dateAndTime, driver;
    Button btn_sendMessage;
    UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        message=findViewById(R.id.message2);
        driver=findViewById(R.id.txDriverName);
        line=findViewById(R.id.editTextLine);
        bus=findViewById(R.id.editTextBus);
        lat=findViewById(R.id.lat4);
        lon=findViewById(R.id.lon4);
        dateAndTime=findViewById(R.id.dateandtime);
        btn_sendMessage=findViewById(R.id.send);

        service = ApiUtils.getAPIService();

        final Messages newMessage =  new Messages();

        Bundle extras = getIntent().getExtras();
        final String username, userId;

        if(extras != null) {
            username = extras.getString("driverName");
            userId = extras.getString("driverId");
            Log.d("uid",userId);

            lat.setText(getIntent().getStringExtra("latitude"));
            lon.setText(getIntent().getStringExtra("longitude"));
            message.setText(getIntent().getStringExtra("uzenet"));
            driver.setText(username);
        }

        service.getMessageType().enqueue(new Callback<List<MessageType>>() {
            @Override
            public void onResponse(Call<List<MessageType>> call, Response<List<MessageType>> response) {
                List<MessageType> messageTypes = response.body();
                for (MessageType m : messageTypes) {
                    final String messageTypeName,messageTypeId;
                    messageTypeName = getIntent().getStringExtra("uzenet");
                    if(m.getMessageTypeName().equals(messageTypeName)){
                        messageTypeId = m.getMessageTypeId();
                        newMessage.setMessageTypeId(messageTypeId.trim());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MessageType>> call, Throwable t) {
                Toast.makeText(Form.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                final String username, userId;

                if (extras != null) {
                    username = extras.getString("driverName");
                }

                userId=getIntent().getStringExtra("driverId");

                newMessage.setDriverId(userId);
                newMessage.setBus(bus.getText().toString().trim());
                newMessage.setLine(line.getText().toString().trim());
                newMessage.setDate(dateAndTime.getText().toString().trim());
                newMessage.setLat(lat.getText().toString().trim());
                newMessage.setLon(lon.getText().toString().trim());

                sendPost(newMessage.getMessageTypeId(),newMessage.getDriverId(),
                        newMessage.getLine(),newMessage.getBus(),newMessage.getDate(),
                        newMessage.getLon(),newMessage.getLat());
            }
        });
    }

    public void getCurrentTime(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        String currentDateandTime = sdf.format(new Date());
        dateAndTime.setText(currentDateandTime);
    }


    public void sendPost(String messageType, String driver, String line, String bus, String date, String lon, String lat) {
        //service = ApiUtils.getAPIService();
        service.sendMessage(messageType,driver,line,bus,date,lon,lat).enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                Log.d("successful","successful");

                if(!response.isSuccessful()) {
                    Log.d("not succesful",response.message());
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Toast.makeText(Form.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("not successful","not successful");
            }
        });
    }
}
