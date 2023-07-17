package com.bibon.furnitureshopping.applications;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoreMapApplication {
    private final double STORE_LATITUDE = 10.841348873595665;
    private final double STORE_LONGITUDE = 106.80990445331318;
    private final String STORE_NAME = "Furniture Store";
    private final String STORE_SLOGAN = "Furniture Store: Quality, Style, Value.";
    private GoogleMap _googleMap;
    private Marker _storeMarker;
    private SupportMapFragment _supportMapFragment;
    private View _mapView;

    public StoreMapApplication(SupportMapFragment supportMapFragment) {
        this._supportMapFragment = supportMapFragment;
        _mapView = supportMapFragment.getView();
    }

    @SuppressLint("MissingPermission")
    public void InitGoogleMap(Context context) {
        _supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                _googleMap = googleMap;
                _googleMap.setMyLocationEnabled(true);
                _googleMap.getUiSettings().setMyLocationButtonEnabled(true);



                SetStoreMarkerLocation();
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

        _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 15));
    }
}
