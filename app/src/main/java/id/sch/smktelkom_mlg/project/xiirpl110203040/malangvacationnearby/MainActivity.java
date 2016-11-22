package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.adapter.WisataAdapter;
import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.model.Wisata;

public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    RecyclerView rvWisata;
    ArrayList<Wisata> wisataList = new ArrayList<>();
    WisataAdapter wisataAdapter;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private Location mCurrentLocation;
    private double longitude;
    private double latitude;

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


        rvWisata = (RecyclerView) findViewById(R.id.rvWisata);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvWisata.setLayoutManager(layoutManager);
        initializeData();
        wisataAdapter = new WisataAdapter(wisataList);
        rvWisata.setAdapter(wisataAdapter);
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
                    Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    Location locationFrom = new Location("From");
                    Location locationTo = new Location("To");
                    if(location != null){

                        locationFrom.setLatitude(location.getLatitude());
                        locationFrom.setLongitude(location.getLongitude());

                        locationTo.setLatitude(map.get("latitude"));
                        locationTo.setLongitude(map.get("longitude"));
                        distance = ((int) locationFrom.distanceTo(locationTo))/1000;
                    }
                    String namaWisata = dataSnapshots.getKey();
                    wisataList.add(new Wisata(namaWisata, distance));
                }
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
                        mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(map.get("nama"))));
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
    public void onClick(View v) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        mMap.setMyLocationEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(location != null){
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        } else {
            LatLng latLng = new LatLng(-7.966697, 112.632509);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    private void moveMap() {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
