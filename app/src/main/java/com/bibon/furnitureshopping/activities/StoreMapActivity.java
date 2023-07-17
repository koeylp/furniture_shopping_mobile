package com.bibon.furnitureshopping.activities;

import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.applications.StoreMapApplication;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class StoreMapActivity extends AppCompatActivity {

    private final double STORE_LATITUDE = 10.841348873595665;
    private final double STORE_LONGITUDE = 106.80990445331318;
    private final float DEFAULT_ZOOM = 17.0f;
    private final String STORE_NAME = "Furniture Store";
    private final String STORE_SLOGAN = "Furniture Store: Quality, Style, Value.";

    private GoogleMap _googleMap;
    private View _mapView;
    private FusedLocationProviderClient _fusedLocationProviderClient;
    private Location _lastKnownLocation;
    private LocationCallback _locationCallback;
    private PlacesClient _placesClient;
    private Marker _storeMarker;
    private SupportMapFragment _supportMapFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.store_map);
        _mapView = mapFragment.getView();
        Places.initialize(StoreMapActivity.this, getString(R.string.google_map_api));
        _placesClient = Places.createClient(this);

        _fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(StoreMapActivity.this);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap googleMap) {
                _googleMap = googleMap;

                _googleMap.setMyLocationEnabled(true);
                _googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                if (_mapView != null && _mapView.findViewById(Integer.parseInt("1")) != null) {
                    View locationButton = ((View) _mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                    layoutParams.setMargins(0, 0, 40, 180);
                }

                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setInterval(10000);
                locationRequest.setFastestInterval(5000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

                SettingsClient settingsClient = LocationServices.getSettingsClient(StoreMapActivity.this);
                Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

                task.addOnSuccessListener(StoreMapActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        GetDeviceLocation();
                    }
                });

                task.addOnFailureListener(StoreMapActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ResolvableApiException) {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            try {
                                resolvable.startResolutionForResult(StoreMapActivity.this, 51);
                            } catch (IntentSender.SendIntentException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    private void SetStoreMarkerLocation()
    {
        LatLng storeLocation = new LatLng(STORE_LATITUDE, STORE_LONGITUDE);

        _storeMarker = _googleMap.addMarker(
                new MarkerOptions()
                        .position(storeLocation)
                        .title(STORE_NAME)
                        .snippet(STORE_SLOGAN)
        );

        _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, DEFAULT_ZOOM));
    }

    @SuppressLint("MissingPermission")
    private void GetDeviceLocation() {
        _fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            _lastKnownLocation = task.getResult();
                            if (_lastKnownLocation != null) {
                                _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(_lastKnownLocation.getLatitude(), _lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                _locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null) {
                                            return;
                                        }
                                        _lastKnownLocation = locationResult.getLastLocation();
                                        _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(_lastKnownLocation.getLatitude(), _lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        _fusedLocationProviderClient.removeLocationUpdates(_locationCallback);
                                    }
                                };
                                _fusedLocationProviderClient.requestLocationUpdates(locationRequest, _locationCallback, null);

                            }
                        } else {
                            Toast.makeText(StoreMapActivity.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
