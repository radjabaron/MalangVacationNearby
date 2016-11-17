package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.DetailActivity;
import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.R;
import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.model.Wisata;

/**
 * Created by LittleFireflies on 06-Nov-16.
 */

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder>{
    public static final String JUDUL = "JUDUL";
    List<Wisata> wisataList;
    String namaWisata;
    Context ctx;

    public WisataAdapter(ArrayList wisataList) {
        this.wisataList = wisataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_wisata, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Wisata wisata = wisataList.get(position);
        holder.tvNamaWisata.setText(wisata.getNamaWisata());
        holder.llWisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, DetailActivity.class);
                intent.putExtra(JUDUL, wisata.getNamaWisata());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(wisataList != null)
            return wisataList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaWisata;
        LinearLayout llWisata;

        public ViewHolder(View itemView) {
            super(itemView);
            ctx = itemView.getContext();
            tvNamaWisata = (TextView) itemView.findViewById(R.id.tvNamaWisata);
            llWisata = (LinearLayout) itemView.findViewById(R.id.llWisata);
        }
    }
}
