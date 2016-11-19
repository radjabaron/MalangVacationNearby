package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.adapter.TaxiAdapter;
import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.model.Taxi;

public class TaxiActivity extends AppCompatActivity {
    RecyclerView rvTaxi;
    ArrayList<Taxi> taxiList = new ArrayList<>();
    TaxiAdapter taxiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi);
        Firebase.setAndroidContext(this);
        rvTaxi = (RecyclerView) findViewById(R.id.rvTaxi);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTaxi.setLayoutManager(layoutManager);
        initializeData();
        taxiAdapter = new TaxiAdapter(taxiList);
        rvTaxi.setAdapter(taxiAdapter);
    }

    private void initializeData() {
        Firebase mRef = new Firebase("https://malang-vacation-nearby-3b8b3.firebaseio.com/taxi");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taxiList.clear();
                for (DataSnapshot dataSnapshots : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshots.getValue(Map.class);
                    String namaTaxi = map.get("nama");
                    String nomorTaxi = map.get("nomor");
                    taxiList.add(new Taxi(namaTaxi, nomorTaxi));
                }
                taxiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
