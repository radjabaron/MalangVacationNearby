package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.R;
import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.model.Wisata;

/**
 * Created by LittleFireflies on 06-Nov-16.
 */

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder>{
    ArrayList<Wisata> wisataList;
    String namaWisata;

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
        Wisata wisata = wisataList.get(position);
        holder.tvNamaWisata.setText(wisata.namaWisata);
    }

    @Override
    public int getItemCount() {
        if(wisataList != null)
            return wisataList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaWisata;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNamaWisata = (TextView) itemView.findViewById(R.id.tvNamaWisata);
        }
    }
}
