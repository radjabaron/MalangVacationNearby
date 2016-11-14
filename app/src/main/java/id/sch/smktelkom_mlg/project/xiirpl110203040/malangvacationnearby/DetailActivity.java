package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.adapter.WisataAdapter;

public class DetailActivity extends AppCompatActivity {

    TextView tvNamaWisata;
    TextView tvAlamat;
    TextView tvTiket;
    TextView tvJam;
    TextView tvDesc;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://malang-vacation-nearby-3b8b3.firebaseio.com/wisata/" + getIntent().getStringExtra(WisataAdapter.JUDUL));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvNamaWisata = (TextView) findViewById(R.id.tvNamaWisata);
                tvAlamat = (TextView) findViewById(R.id.tvAddress);
                tvTiket = (TextView) findViewById(R.id.tvTicket);
                tvJam = (TextView) findViewById(R.id.tvOpen);
                tvDesc = (TextView) findViewById(R.id.tvDesc);


                Map<String, String> map = dataSnapshot.getValue(Map.class);
                tvNamaWisata.setText(map.get("nama"));
                tvAlamat.setText(map.get("alamat"));
                tvTiket.setText(map.get("harga"));
                tvJam.setText(map.get("jam"));
                tvDesc.setText(map.get("desc"));

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

}
