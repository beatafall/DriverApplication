package com.example.terkepes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.terkepes.Class.BusLineDriver;
import com.example.terkepes.Retrofit.ApiUtils;
import com.example.terkepes.Retrofit.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.format;

public class ViewMessagesFromAdmin extends AppCompatActivity {

    UserService service;
    TextView messageItems;
    Button btn_delete;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages_from_admin);

        service = ApiUtils.getAPIService();

        Bundle extras = getIntent().getExtras();
        final String username, userId;

        messageItems = findViewById(R.id.busItemsTxt);
        btn_delete = findViewById(R.id.delete);
        swipeRefreshLayout = findViewById(R.id.linearLayout);

        if(extras != null) {
            username = extras.getString("driverName");
            userId = extras.getString("driverId");

            service.getAllBusLineDriver().enqueue(new Callback<List<BusLineDriver>>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<List<BusLineDriver>> call, Response<List<BusLineDriver>> response) {

                    if(!response.isSuccessful()){
                        Log.d("not successful", response.message());
                        return;
                    }
                    final List<BusLineDriver> drivers = response.body();
                    int k=1;
                    for(final BusLineDriver d : drivers) {
                        if (d.getSoforId().equals(userId)) {
                            String content = k + ".)  "+ "Indulás ideje ";
                            content += d.getDatum() + ", a ";
                            content += d.getVonalId() + " vonal, ";
                            content += d.getBuszId() + " számú busszal." + "\n\n";
                            k++;

                            messageItems.append(content);
                       }

                    }

                    btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int deletedItem;
                            for(final BusLineDriver d : drivers) {
                                if (d.getSoforId().toString().equals(userId)) {
                                    deletedItem = d.getVonalbuszsoforId();
                                    deleteBusLineDriver(deletedItem);
                                    break;
                                }
                            }
                            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override public void run() {
                                            swipeRefreshLayout.setRefreshing(false);
                                        }
                                    }, 5000);
                                }
                            });
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<BusLineDriver>> call, Throwable t) {
                    Toast.makeText(ViewMessagesFromAdmin.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
            bottomNavigationView.setSelectedItemId(R.id.newMessage);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.sendMessage:
                            Intent intent = new Intent(ViewMessagesFromAdmin.this, SendNewMessage.class);
                            intent.putExtra("latitude", getIntent().getStringExtra("latitude"));
                            intent.putExtra("longitude", getIntent().getStringExtra("longitude"));
                            intent.putExtra("driverId", userId);
                            intent.putExtra("driverName", username);
                            startActivity(intent);
                            break;
                        case R.id.newMessage:
                            break;
                        case R.id.searchLine:
                            Intent intent2 = new Intent(ViewMessagesFromAdmin.this, SeachLine.class);
                            intent2.putExtra("latitude", getIntent().getStringExtra("latitude"));
                            intent2.putExtra("longitude", getIntent().getStringExtra("longitude"));
                            intent2.putExtra("driverId", userId);
                            intent2.putExtra("driverName", username);
                            startActivity(intent2);
                            break;
                    }
                    return true;
                }
            });
        }
    }

    public void deleteBusLineDriver(int id){

        service.deleteBLD(id).enqueue(new Callback<BusLineDriver>() {
            @Override
            public void onResponse(Call<BusLineDriver> call, Response<BusLineDriver> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ViewMessagesFromAdmin.this, "Sikeresen törölte a legutóbbi üzenetet!", Toast.LENGTH_SHORT).show();
                    Log.d("successful","successful");
                }
            }

            @Override
            public void onFailure(Call<BusLineDriver> call, Throwable t) {
                Toast.makeText(ViewMessagesFromAdmin.this, "Sikeresen törölte a legrégebbi üzenetet!", Toast.LENGTH_SHORT).show();
                Log.d("not successful","not successful");
            }
        });

    }

}
