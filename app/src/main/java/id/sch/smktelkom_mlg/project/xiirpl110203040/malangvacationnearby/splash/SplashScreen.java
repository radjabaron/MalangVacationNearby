package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.MainActivity;
import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.R;

/**
 * Created by Nadiawo on 07/11/2016.
 */

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
