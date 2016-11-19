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
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.ChildEventListener;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private Location mCurrentLocation;
    private double longitude;
    private double latitude;
    RecyclerView rvWisata;
    ArrayList<Wisata> wisataList = new ArrayList<>();
    WisataAdapter wisataAdapter;

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
        Firebase mRef = new Firebase("https://malang-vacation-nearby-3b8b3.firebaseio.com/wisata");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wisataList.clear();
                for (DataSnapshot dataSnapshots : dataSnapshot.getChildren()) {
                    String namaWisata = dataSnapshots.getKey();
                    wisataList.add(new Wisata(namaWisata));
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
                TextView tvNoConnection = (TextView)findViewById(R.id.tvNoConnection);
                tvNoConnection.setVisibility(View.GONE);
                for(DataSnapshot dataSnapshots : dataSnapshot.getChildren()){
                    Map<String, Double> map = dataSnapshots.getValue(Map.class);
                    longitude = map.get("longitude");
                    latitude = map.get("latitude");
                    LatLng latLng = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(map.get("nama"))));
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
