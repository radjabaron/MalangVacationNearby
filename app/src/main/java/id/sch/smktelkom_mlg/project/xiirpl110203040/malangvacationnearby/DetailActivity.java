package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    Button btnOpenMaps;
    String googleMap = "com.google.android.apps.maps";
    Uri gmmIntentUri;
    Intent mapIntent;
    String masjid_agung_demak = "-6.894649906672214,110.63718136399984";



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

        findViewById(R.id.btnOpenTaxi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, TaxiActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnOpenMaps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmmIntentUri = Uri.parse("google.navigation:q=" + masjid_agung_demak);

                // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                mapIntent.setPackage(googleMap);

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(DetailActivity.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
