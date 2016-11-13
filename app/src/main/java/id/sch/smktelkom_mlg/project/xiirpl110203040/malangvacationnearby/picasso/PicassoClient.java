package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.picasso;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.R;

/**
 * Created by LittleFireflies on 11-Nov-16.
 */

public class PicassoClient {
    public static void downloadImage(Context ctx, String url, ImageView ivWisata){
        if(url != null && url.length()>0){
            Picasso.with(ctx).load(url).placeholder(R.drawable.placeholder).into(ivWisata);
        } else {
            Picasso.with(ctx).load(R.drawable.placeholder).into(ivWisata);
        }
    }
}
