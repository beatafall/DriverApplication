package com.example.terkepes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.terkepes.Class.BusesOnTheRoad;
import com.example.terkepes.Class.LineStations;
import com.example.terkepes.Retrofit.ApiUtils;
import com.example.terkepes.Retrofit.UserService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewLine extends AppCompatActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    GoogleMap mMap;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_line);

        userService= ApiUtils.getAPIService();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        Bundle extras = getIntent().getExtras();
        final String username, userId, latitude, longitude;

        if(extras != null) {
            username = extras.getString("driverName");
            userId = extras.getString("driverId");
            latitude = extras.getString("latitude");
            longitude = extras.getString("longitude");

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
            bottomNavigationView.setSelectedItemId(R.id.searchLine);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.newMessage:
                            Intent intent = new Intent(ViewLine.this, ViewMessagesFromAdmin.class);
                            intent.putExtra("latitude", latitude);
                            intent.putExtra("longitude", longitude);
                            intent.putExtra("driverId", userId);
                            intent.putExtra("driverName", username);
                            startActivity(intent);
                            break;
                        case R.id.searchLine:
                            Intent intent2 = new Intent(ViewLine.this, SeachLine.class);
                            intent2.putExtra("latitude", latitude);
                            intent2.putExtra("longitude", longitude);
                            intent2.putExtra("driverId", userId);
                            intent2.putExtra("driverName", username);
                            startActivity(intent2);
                            break;
                        case R.id.sendMessage:
                            Intent intent3 = new Intent(ViewLine.this, SendNewMessage.class);
                            intent3.putExtra("latitude", latitude);
                            intent3.putExtra("longitude", longitude);
                            intent3.putExtra("driverId", userId);
                            intent3.putExtra("driverName", username);
                            startActivity(intent3);
                            break;
                    }
                    return true;
                }
            });

        }


        userService.getLineStations().enqueue(new Callback<List<LineStations>>() {
            @Override
            public void onResponse(Call<List<LineStations>> call, Response<List<LineStations>> response) {
                List<LineStations> lineStations = response.body();

                Bundle extras = getIntent().getExtras();

                if(extras != null) {
                    String line = getIntent().getStringExtra("line");
                    for (LineStations l : lineStations) {
                        if(l.getLineId().toString().equals(line)){

                            MarkerOptions markerOptions = new MarkerOptions();

                            LatLng latLng = new LatLng(l.getLat(), l.getLon());
                            markerOptions.position(latLng);
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                            markerOptions.title(l.getStationName());
                            Marker m = mMap.addMarker(markerOptions);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<LineStations>> call, Throwable t) {
                Toast.makeText(ViewLine.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<BusesOnTheRoad>> callBusesOnTheRoad = userService.getBuses();
        callBusesOnTheRoad.enqueue(new Callback<List<BusesOnTheRoad>>() {
            @Override
            public void onResponse(Call<List<BusesOnTheRoad>> call, Response<List<BusesOnTheRoad>> response) {
                List<BusesOnTheRoad> buses = response.body();

                Bundle extras = getIntent().getExtras();

                if(extras != null) {
                    String line = getIntent().getStringExtra("line");
                    for (BusesOnTheRoad bus : buses) {
                        Log.d("h",bus.getLine().toString());
                        if(bus.getLine().toString().equals(line)){
                            Log.d("h2",bus.getLine().toString());
                            MarkerOptions markerOptions2 = new MarkerOptions();

                            LatLng latLng = new LatLng(bus.getLat(), bus.getLon());

                            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                            markerOptions2.icon(bitmapDescriptor);
                            markerOptions2.position(latLng);
                            markerOptions2.title( "Idő: " + bus.getDate());
                            Marker m = mMap.addMarker(markerOptions2);

                            m.showInfoWindow();

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<BusesOnTheRoad>> call, Throwable t) {
                Toast.makeText(ViewLine.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    //Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + ", " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(ViewLine.this);
                }
            }
        });
    }

    public void onMapReady(final GoogleMap googleMap) {
        mMap=googleMap;

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Tartozkodási hely");

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);

        markerOptions.icon(bitmapDescriptor);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }
}
