package com.example.diogenes.samplemaps;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends android.support.v4.app.FragmentActivity implements OnMapClickListener {

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

    }

    @Override
    protected void onResume() {
        super.onResume();

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

//                mMap.setMyLocationEnabled(true);

        final CameraPosition position = new CameraPosition.Builder()
                .target(fafica) 	// Localização
                .bearing(0)	 	// Direção em que a cÂmera está apontando em graus
                .tilt(0) 			// Ângulo que a cÂmera está posicionada em graus (0 a 90)
                .zoom(18) 			// Zoom
                .build();

        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);

        // Centraliza o mapa com animação de 10 segundos
        map.animateCamera(update);

        // Centraliza o mapa com animação de 10 segundos
        map.animateCamera(update);

        // Eventos
        map.setOnMapClickListener(this);


    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker
        LatLng etepam = new LatLng(-8.039431, -34.893179);
        *//*mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*//*


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(etepam,13);

        mMap.moveCamera(update);

        mMap.addMarker(new MarkerOptions()
        .title("Melhores alunos de ADS")
        .snippet("estão na fafica")
        .position(etepam)
//        .icon(new BitmapDescriptor())
        );

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }*/
}