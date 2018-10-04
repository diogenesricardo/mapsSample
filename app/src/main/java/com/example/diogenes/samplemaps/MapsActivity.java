package com.example.diogenes.samplemaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public class MapsActivity extends android.support.v4.app.FragmentActivity implements OnMapClickListener {

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                iniciarMapa(googleMap);
            }
        });
    }

    private void iniciarMapa(GoogleMap map) {

        // Configura o tipo do mapa
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Add a marker
        LatLng fafica = new LatLng(-8.298635, -35.974063);

//        map.setMyLocationEnabled(true);

        final CameraPosition position = new CameraPosition.Builder()
                .target(fafica) 	// Localização
                .bearing(0)	 	// Direção em que a cÂmera está apontando em graus
                .tilt(0) 			// Ângulo que a cÂmera está posicionada em graus (0 a 90)
                .zoom(18) 			// Zoom
                .build();

        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);

        // Centraliza o mapa com animação de 10 segundos
        map.animateCamera(update);

        // Eventos
//        map.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

}