package com.example.diogenes.samplemaps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diogenes.samplemaps.util.PermissionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;


public class MapsActivity extends android.support.v4.app.FragmentActivity implements OnMapClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    SupportMapFragment mapFragment;
    GoogleApiClient mGoogleApiClient;
    TextView tvMinhaCoordenada;
    GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        solicitarPermissoes();

        // Configura o objeto GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        // Método que dispara uma intent para o maps com um rota desejada.
//        tracarRota();

//        tracarRotaManualmente();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
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
                /*tvMinhaCoordenada.setText("Latitude " + String.valueOf(location.getLatitude())
                        + " Longitude " + String.valueOf(location.getLongitude()));*/
                setMapLocation(location);
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
        // Iniciando o GPS
        startLocationUpdates();
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

    private void setMapLocation(Location l) {
        if (map != null && l != null) {
            LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            map.animateCamera(update);

            TextView text = findViewById(R.id.lblCurrentLocation);
            String s = String.format("Última atualização %s", DateFormat.getTimeInstance().format(new Date()));
            s += String.format("\nLat/Lnt %f/%f, provider: %s", l.getLatitude(), l.getLongitude(), l.getProvider());
            text.setText(s);

            // Desenha uma bolinha vermelha nos ponto que passou
            CircleOptions circle = new CircleOptions().center(latLng);
            circle.fillColor(Color.BLUE);
            circle.radius(2); // Em metros
//            map.clear();
            map.addCircle(circle);

        }
    }

    protected void startLocationUpdates() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // Faça alguma coisa qui
                onLocationChanged(locationResult.getLastLocation());
            }
        }, Looper.myLooper());
    }

    protected void stopLocationUpdates() {
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // Faça alguma coisa qui
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Localização atualizada: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        setMapLocation(location);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Para o GPS
        stopLocationUpdates();
        // Desconecta do Google.
        mGoogleApiClient.disconnect();
    }

    private void tracarRota(){
        String origem = "-8.304436, -35.983375";
        String destino = "-8.298635, -35.974063";
        String url = "http://maps.google.com/maps?f=d&saddr="+origem+"&daddr="+destino+"&hl=pt";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    private void tracarRotaManualmente() {
        String origem = "-8.304436, -35.983375";
        String destino = "-8.298635, -35.974063";
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+origem+"&destination="+destino
                +"&sensor=true&mode=driving&key="+R.string.google_maps_key;

        getJsonCoordenadasRotas(url);

    }

    private void getJsonCoordenadasRotas(final String url) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here

                try {
                    // 01 - Create URL
                    URL getInsumosURL = new URL(url);

                    // 02 - Create connection
                    HttpURLConnection myConnection =
                            (HttpURLConnection) getInsumosURL.openConnection();

                    // 03 - InputStream do corpo da resposta
                    InputStream responseBody = myConnection.getInputStream();

                    // 04 - Leitor do Stream
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");

                    //AUDIO, PDF, IMAGEM

                    // 05 - Leitor de JSON
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    // 06 - Iterar
                    jsonReader.beginObject();

                    Log.v("Json",jsonReader.toString());

                    // 07 - Fechando o leitor de JSON
                    jsonReader.close();

                    // 08 - Fechando a conexão
                    myConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}