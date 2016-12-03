package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.adapter.WisataAdapter;
import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.model.Wisata;

public class MainActivity extends FragmentActivity implements
        LocationListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static GoogleMap mMap;
    ImageView ivAbout;
    RecyclerView rvWisata;
    ArrayList<Wisata> wisataList = new ArrayList<>();
    WisataAdapter wisataAdapter;
    Location location;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location mCurrentLocation;
    private double longitude;
    private double latitude;

    public static void animate(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


        ivAbout = (ImageView) findViewById(R.id.ivAbout);
        rvWisata = (RecyclerView) findViewById(R.id.rvWisata);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvWisata.setLayoutManager(layoutManager);

        wisataAdapter = new WisataAdapter(wisataList);
        rvWisata.setAdapter(wisataAdapter);

        ivAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });
    }

    private void initializeData() {
        final Firebase mRef = new Firebase("https://malang-vacation-nearby-3b8b3.firebaseio.com/wisata");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wisataList.clear();
                for (DataSnapshot dataSnapshots : dataSnapshot.getChildren()) {
                    Map<String, Double> map = dataSnapshots.getValue(Map.class);
                    int distance = 0;
                    location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    Location locationFrom = new Location("From");
                    Location locationTo = new Location("To");
                    if(location != null){

                        Log.d("LOCATION", String.valueOf(location.getLatitude()));
                        Log.d("LOCATION", String.valueOf(location.getLongitude()));
                        locationFrom.setLatitude(location.getLatitude());
                        locationFrom.setLongitude(location.getLongitude());

                        locationTo.setLatitude(map.get("latitude"));
                        locationTo.setLongitude(map.get("longitude"));

                    }
                    distance = ((int) locationFrom.distanceTo(locationTo)) / 1000;
                    String namaWisata = dataSnapshots.getKey();
                    wisataList.add(new Wisata(namaWisata, distance));
                }
                Log.d("LOCATION", "STOP");
                wisataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();

    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*LatLng latLng = new LatLng(-7.977205, 112.658870);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Moklet"));*/

        Firebase mRef = new Firebase("https://malang-vacation-nearby-3b8b3.firebaseio.com/wisata");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView tvNoConnection = (TextView) findViewById(R.id.tvNoConnection);
                tvNoConnection.setVisibility(View.GONE);
                for (DataSnapshot dataSnapshots : dataSnapshot.getChildren()) {
                    Map<String, Double> map = dataSnapshots.getValue(Map.class);
                    try{
                        longitude = map.get("longitude");
                        latitude = map.get("latitude");
                        LatLng latLng = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(map.get("nama"))).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_mvn3)));
                    } catch (Exception e){
                        continue;
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            initializeData();
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
//        LatLng latLng = new LatLng(-7.966697, 112.632509);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        getCurrentLocation();
    }
}
