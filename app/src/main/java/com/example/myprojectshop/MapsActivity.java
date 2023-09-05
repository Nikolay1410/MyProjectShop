package com.example.myprojectshop;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myprojectshop.databinding.ActivityMapsBinding;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_CODE = 101;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String shopName;
    private String personNameShop;
    private double latitudeClick;
    private double longitudeClick;

    List<Address> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLocationPermissionGranted();
        Intent intent = getIntent();
        if (intent.hasExtra("myShopName") && intent.hasExtra("myPersonShop")) {
            shopName = intent.getStringExtra("myShopName");
            personNameShop = intent.getStringExtra("myPersonShop");
        }

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                Marker melbourneHhh = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(shopName)
                        //Описание
                        .snippet(personNameShop)
                        //Делаем маркер перетаскиваемым
                        .draggable(true)
                        //Цвет маркера
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                //Показать информ.по маркеру.
                melbourneHhh.showInfoWindow();
                //Перейти к даному маркеру
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                latitudeClick = (double) melbourneHhh.getPosition().latitude;
                longitudeClick = (double) melbourneHhh.getPosition().longitude;
            }
        });
    }

    public void onClickAddShopOnMap(View view) {
        Intent addIntent = new Intent(getApplicationContext(), AddShop.class);
        addIntent.putExtra("shopName", shopName);
        addIntent.putExtra("personNameShop", personNameShop);
        addIntent.putExtra("latitude", latitudeClick);
        addIntent.putExtra("longitude", longitudeClick);
        startActivity(addIntent);
//        Marker melbourne = mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney")
//                //Описание
//                .snippet("Population: 4,137,400")
//                //Цвет маркера
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//        //Показать информ.по маркеру.
//        melbourne.showInfoWindow();
//        //Перейти к даному маркеру
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                //Переходим в гугл карты(приложение)
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("https://www.google.com/maps"));
//                startActivity(intent);
                return false;
            }
        });
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps"));
        //startActivity(intent);
    }
    private boolean isLocationPermissionGranted(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else {
            return  false;
        }
    }
    private void requestLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch ( requestCode ) {
            case LOCATION_PERMISSION_CODE: {
                for( int i = 0; i < permissions.length; i++ ) {
                    if( grantResults[i] == PackageManager.PERMISSION_GRANTED ) {
                        recreate();
                    } else if( grantResults[i] == PackageManager.PERMISSION_DENIED ) {
                        finishAffinity();
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}