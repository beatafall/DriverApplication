package com.example.terkepes;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    TextView lat, lon, driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        lat=findViewById(R.id.lat2);
        lon=findViewById(R.id.lon2);
        driver=findViewById(R.id.driver);

        Bundle extras = getIntent().getExtras();
        final String username, userId;

        if(extras != null) {
            username = extras.getString("driver");
            userId = extras.getString("id");
            driver.setText("Üdvözöljük, " + username + "!");
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
            bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.newMessage:
                            Intent intent = new Intent(Map.this, ViewMessagesFromAdmin.class);
                            intent.putExtra("latitude", lat.getText().toString());
                            intent.putExtra("longitude", lon.getText().toString());
                            intent.putExtra("driverId", userId);
                            intent.putExtra("driverName", username);
                            startActivity(intent);
                            break;
                        case R.id.searchLine:
                            Intent intent2 = new Intent(Map.this, SeachLine.class);
                            intent2.putExtra("latitude", lat.getText().toString());
                            intent2.putExtra("longitude", lon.getText().toString());
                            intent2.putExtra("driverId", userId);
                            intent2.putExtra("driverName", username);
                            startActivity(intent2);
                            break;
                        case R.id.sendMessage:
                            Intent intent3 = new Intent(Map.this, SendNewMessage.class);
                            intent3.putExtra("latitude", lat.getText().toString());
                            intent3.putExtra("longitude", lon.getText().toString());
                            intent3.putExtra("driverId", userId);
                            intent3.putExtra("driverName", username);
                            startActivity(intent3);
                            break;
                    }
                    return true;
                }
            });

        }
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    supportMapFragment.getMapAsync(Map.this);
                }
            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        double latitude= currentLocation.getLatitude();
        double longitude= currentLocation.getLongitude();

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here");

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        googleMap.addMarker(markerOptions);

        lat.setText("" + latitude);
        lon.setText("" + longitude);
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
