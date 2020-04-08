package com.example.terkepes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.terkepes.Class.BusLineDriver;
import com.example.terkepes.Class.Driver;
import com.example.terkepes.Retrofit.ApiUtils;
import com.example.terkepes.Retrofit.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                @Override
                public void onResponse(Call<List<BusLineDriver>> call, Response<List<BusLineDriver>> response) {

                    if(!response.isSuccessful()){
                        Log.d("not successful", response.message());
                        return;
                    }
                    final List<BusLineDriver> drivers = response.body();
                    for(final BusLineDriver d : drivers) {
                        if (d.getSoforId().toString().equals(userId)) {
                            String content = "";
                            content += "Busz: " + d.getBuszId() + "\n";
                            content += "Vonal: " + d.getVonalId() + "\n";
                            content += "Idopont: " + d.getDatum() + "\n\n";

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
                            Intent intent = new Intent(ViewMessagesFromAdmin.this, Message.class);
                            intent.putExtra("latitude", getIntent().getStringExtra("latitude"));
                            intent.putExtra("longitude", getIntent().getStringExtra("longitude"));
                            intent.putExtra("driverId", userId);
                            intent.putExtra("driverName", username);
                            startActivity(intent);
                            break;
                        case R.id.newMessage:
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
