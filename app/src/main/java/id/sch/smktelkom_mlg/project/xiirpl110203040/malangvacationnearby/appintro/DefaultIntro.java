package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.appintro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.MainActivity;
import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.R;

/**
 * Created by Nadiawo on 07/12/2016.
 */
public class DefaultIntro extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//Down here, we add the xml layouts into sample slide inflater.
        addSlide(ScreenSlider.newInstance(R.layout.intro));
        addSlide(ScreenSlider.newInstance(R.layout.intro2));
        addSlide(ScreenSlider.newInstance(R.layout.intro3));

        showStatusBar(true);

        setDepthAnimation();

    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        loadMainActivity();
    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadMainActivity();
        Toast.makeText(getApplicationContext(), "Skip", Toast.LENGTH_SHORT).show();
    }

    public void getStarted(View v) {
        loadMainActivity();
    }
}
