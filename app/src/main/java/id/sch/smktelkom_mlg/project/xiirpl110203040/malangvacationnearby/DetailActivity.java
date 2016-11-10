package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.adapter.WisataAdapter;

public class DetailActivity extends AppCompatActivity {
    TextView tvNamaWisata;
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_wisata);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://malang-vacation-nearby-3b8b3.firebaseio.com/wisata/" + getIntent().getStringExtra(WisataAdapter.JUDUL)+"/nama");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvNamaWisata = (TextView) findViewById(R.id.tvNamaWisata);
                tvNamaWisata.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
