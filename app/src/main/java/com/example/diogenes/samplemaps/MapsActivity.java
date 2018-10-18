package com.example.diogenes.samplemaps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class MapsActivity extends android.support.v4.app.FragmentActivity implements OnMapClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    SupportMapFragment mapFragment;
    GoogleApiClient mGoogleApiClient;
    TextView tvMinhaCoordenada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        solicitarPermissoes();

        // Configura o objeto GoogleApiClient
/*        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();*/

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

        // Solicitando permissões
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);

        // Add a marker
        LatLng fafica = new LatLng(-8.298635, -35.974063);
        LatLng iterativo = new LatLng(-8.298359, -35.973456);
//        LatLng marcozero = new LatLng(-8.071709, -34.877724);

// DETALHES DE INCLINAÇÃO E DIREÇÃO DA CÂMERA
        final CameraPosition position = new CameraPosition.Builder()
                .target(fafica)    // Localização
                .bearing(0)            // Direção em que a cÂmera está apontando em graus
                .tilt(0)            // Ângulo que a cÂmera está posicionada em graus (0 a 90)
                .zoom(19)            // Zoom
                .build();

        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(fafica,18);

// Centraliza o mapa
        map.animateCamera(update);

// MANIPULAR EVENTOS NA ANIMAÇÃO DO MAPA
/*        map.animateCamera(update, 10*1000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Mapa chegou ao ponto desejado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Animação cancelada", Toast.LENGTH_LONG).show();
            }
        });*/

// ADICIONAR MARCADOR
//        adicionarMarcador(map,fafica);

// DESENHA UMA LINHA ENTRE DOIS PONTOS
//        adicionaLinha(map,fafica,iterativo);

// DESENHA UM POLÍGONO COM BASE EM 3 OU MAIS PONTOS
//        adicionaPoligno(map, fafica, iterativo);

// EVENTO NO MARCADOR
        /*map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(),"Evento de click no marcador",Toast.LENGTH_LONG).show();
                return false;
            }
        });

// EVENTO NA JANELA DO MARCADOR
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(),"Evento de click na janela",Toast.LENGTH_LONG).show();
            }
        });*/
// Eventos
//        map.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada, agora é com você :-)
                alertAndFinish();
                return;
            }
        }
        // Se chegou aqui está OK :-)
    }

    private void alertAndFinish() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, você precisa aceitar as permissões.");
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    private void adicionarMarcador(GoogleMap googleMap, LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title("Turma ADS").snippet("Fafica");

        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

        googleMap.addMarker(markerOptions);
    }

    private void adicionaLinha(GoogleMap googleMap, LatLng marca1, LatLng marca2) {
        PolylineOptions line = new PolylineOptions();
        line.add(new LatLng(marca1.latitude, marca1.longitude));
        line.add(new LatLng(marca2.latitude, marca2.longitude));
        line.color(Color.BLUE);
        Polyline polyline = googleMap.addPolyline(line);
    }

    private void adicionaPoligno(GoogleMap googleMap, LatLng marca1, LatLng marca2) {
        PolygonOptions p = new PolygonOptions();
        p.add(marca1);
        p.add(marca2);
        p.add(new LatLng(-8.297540, -35.973760));
        //MAIS PONTOS AQUI
        p.strokeColor(Color.GREEN);
        Polygon polygon = googleMap.addPolygon(p);
        polygon.setFillColor(Color.BLUE);
    }

    @SuppressLint("MissingPermission")
    public void getMinhaCoordenada(View view) {
        tvMinhaCoordenada = (TextView) findViewById(R.id.lblMinhaCoordenada);

        LocationServices.getFusedLocationProviderClient(this).getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                tvMinhaCoordenada.setText("Latitude " + String.valueOf(location.getLatitude())
                        + " Longitude " + String.valueOf(location.getLongitude()));
            }
        });

        /*client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                tvMinhaCoordenada.setText("Latitude " + String.valueOf(location.getLatitude())
                        + " Longitude " + String.valueOf(location.getLongitude())
                );
            }
        });*/
    }

    private void solicitarPermissoes() {
        // Solicita as permissões
        String[] permissoes = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        PermissionUtils.validate(this, 0, permissoes);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
// Conecta no Google Play Services
// Podemos utilizar qualquer API agora
        Toast.makeText(getBaseContext(), "Conexão ao PlayServices com sucesso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
// A conexão foi interrompida
// A aplicação precisa aguardar até a conexão ser restabelecida
        Toast.makeText(getBaseContext(), "Conexão interrompida", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
// Erro na conexão
// Pode ser configuração inválida ou falta de conectividade no dispositivo
        Toast.makeText(getBaseContext(), "Falha ao conectar serviço", Toast.LENGTH_SHORT).show();
    }
}