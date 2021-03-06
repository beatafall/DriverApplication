package com.example.terkepes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SendNewMessage extends AppCompatActivity {

    TextView lat, lon;
    Button btn_lerobbanas, btn_lopas, btn_baleset, btn_forgalom, btn_tulzsofoltsag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        lat=findViewById(R.id.lat3);
        lon=findViewById(R.id.lon3);
        btn_baleset=findViewById(R.id.baleset);
        btn_forgalom=findViewById(R.id.forgalom);
        btn_lerobbanas=findViewById(R.id.lerobbanas);
        btn_lopas=findViewById(R.id.lopas);
        btn_tulzsofoltsag=findViewById(R.id.tulzsufoltsag);

        Bundle extras = getIntent().getExtras();
        final String username, userId;

        if(extras != null) {
            username = extras.getString("driverName");
            userId = extras.getString("driverId");

            lat.setText(getIntent().getStringExtra("latitude"));
            lon.setText(getIntent().getStringExtra("longitude"));

            btn_baleset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Form.class);
                    intent.putExtra("latitude", lat.getText().toString());
                    intent.putExtra("longitude", lon.getText().toString());
                    intent.putExtra("uzenet", "baleset");
                    intent.putExtra("driverId", userId);
                    intent.putExtra("driverName", username);
                    startActivity(intent);
                }
            });

            btn_tulzsofoltsag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Form.class);
                    intent.putExtra("latitude", lat.getText().toString());
                    intent.putExtra("longitude", lon.getText().toString());
                    intent.putExtra("uzenet", "tulzsufoltsag a buszon");
                    intent.putExtra("driverId", userId);
                    intent.putExtra("driverName", username);
                    startActivity(intent);
                }
            });

            btn_lopas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Form.class);
                    intent.putExtra("latitude", lat.getText().toString());
                    intent.putExtra("longitude", lon.getText().toString());
                    intent.putExtra("uzenet", "lopas");
                    intent.putExtra("driverId", userId);
                    intent.putExtra("driverName", username);
                    startActivity(intent);
                }
            });

            btn_lerobbanas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Form.class);
                    intent.putExtra("latitude", lat.getText().toString());
                    intent.putExtra("longitude", lon.getText().toString());
                    intent.putExtra("uzenet", "lerobbanas");
                    intent.putExtra("driverId", userId);
                    intent.putExtra("driverName", username);
                    startActivity(intent);
                }
            });

            btn_forgalom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Form.class);
                    intent.putExtra("latitude", lat.getText().toString());
                    intent.putExtra("longitude", lon.getText().toString());
                    intent.putExtra("uzenet", "forgalom");
                    intent.putExtra("driverId", userId);
                    intent.putExtra("driverName", username);
                    startActivity(intent);
                }
            });

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
            bottomNavigationView.setSelectedItemId(R.id.sendMessage);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case  R.id.newMessage:
                            Intent intent = new Intent(SendNewMessage.this, ViewMessagesFromAdmin.class);
                            intent.putExtra("driverId", userId);
                            intent.putExtra("latitude", lat.getText().toString());
                            intent.putExtra("longitude", lon.getText().toString());
                            intent.putExtra("driverName", username);
                            startActivity(intent);
                            break;
                        case R.id.searchLine:
                            Intent intent2 = new Intent(SendNewMessage.this, SeachLine.class);
                            intent2.putExtra("latitude", lat.getText().toString());
                            intent2.putExtra("longitude", lon.getText().toString());
                            intent2.putExtra("driverId", userId);
                            intent2.putExtra("driverName", username);
                            startActivity(intent2);
                            break;
                        case R.id.sendMessage:
                            break;
                    }
                    return true;
                }
            });


        }
    }
}
