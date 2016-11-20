package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.picasso;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.R;

/**
 * Created by Nadiawo on 17/11/2016.
 */

public class PicassoClient {
    public static void downloadImage(Context ctx, String url, ImageView ivWisata){
        if(url != null && url.length()>0){
            Picasso.with(ctx).load(url).placeholder(R.drawable.load).into(ivWisata);
        } else {
            Picasso.with(ctx).load(R.drawable.load).into(ivWisata);
        }
    }
}
