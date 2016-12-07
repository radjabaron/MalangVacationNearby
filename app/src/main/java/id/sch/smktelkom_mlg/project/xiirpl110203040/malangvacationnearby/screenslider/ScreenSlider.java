package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.screenslider;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by hananaul on 12/7/2016.
 */

public class ScreenSlider extends Fragment {
    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;

    public ScreenSlider() {
    }

    public static ScreenSlider newInstance(int layoutResId) {
        ScreenSlider sampleSlide = new ScreenSlider();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }
}
