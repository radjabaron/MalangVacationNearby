package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class InputWisata extends AppCompatActivity {

    Firebase wisata;
    Button btninput;
    EditText edtext;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_wisata);
        Firebase.setAndroidContext(this); // menyalakan firebase
        btninput = (Button) findViewById(R.id.button);
        edtext = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.textView);
        wisata = new Firebase("https://malang-vacation-nearby-3b8b3.firebaseio.com/"); // ngambil firebase


        btninput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map data = new HashMap(); //push data
                data.put("fasilitas", "renang"); //diganti variabel
                data.put("form", edtext.getText().toString()); //nah

                wisata.child("wisata").child("0").push().setValue(data); //masuk ke dalam data firebase, (hirarki)
            }
        });

        wisata.child("wisata").child("1").child("fasilitas").addValueEventListener(new ValueEventListener() { //untuk view database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
