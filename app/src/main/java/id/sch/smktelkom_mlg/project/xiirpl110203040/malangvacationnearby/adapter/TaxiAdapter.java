package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.R;
import id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.model.Taxi;

/**
 * Created by BARON on 11/13/2016.
 */

public class TaxiAdapter extends RecyclerView.Adapter<TaxiAdapter.ViewHolder> {
    List<Taxi> taxiList;
    String namataxi;
    String nomortaxi;
    Context ctx;

    public TaxiAdapter(ArrayList wisataList) {
        this.taxiList = wisataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_taxi, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Taxi taxi = taxiList.get(position);
        holder.tvNamaTaxi.setText(taxi.getNamaTaxi());
        holder.tvNomorTaxi.setText(taxi.getNomorTaxi());
        holder.btnCallTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(holder.tvNomorTaxi.getText().toString()));
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (taxiList != null)
            return taxiList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaTaxi;
        TextView tvNomorTaxi;
        ImageButton btnCallTaxi;
        CardView cvTaxi;

        public ViewHolder(View itemView) {
            super(itemView);
            ctx = itemView.getContext();
            tvNamaTaxi = (TextView) itemView.findViewById(R.id.tvNamaTaxi);
            tvNomorTaxi = (TextView) itemView.findViewById(R.id.tvNomorTaxi);
            btnCallTaxi = (ImageButton) itemView.findViewById(R.id.btnCallTaxi);
            cvTaxi = (CardView) itemView.findViewById(R.id.cvTaxi);
        }
    }
}
