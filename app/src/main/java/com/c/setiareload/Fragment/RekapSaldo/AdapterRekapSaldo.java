package com.c.setiareload.Fragment.RekapSaldo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.konter.Mkonter;

import java.util.ArrayList;

public class AdapterRekapSaldo extends RecyclerView.Adapter<AdapterRekapSaldo.ViewHolder> {


    Context context;
    ArrayList<responRekap.Data.Item> data;


    public AdapterRekapSaldo(Context context, ArrayList<responRekap.Data.Item> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrekapsaldo, parent, false);
        AdapterRekapSaldo.ViewHolder holder = new AdapterRekapSaldo.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        responRekap.Data.Item dataa = data.get(position);
        if(dataa.getType_nominal().equals("KREDIT")){
            holder.hargaRS.setTextColor(Color.rgb(17, 173, 64));
        }

        holder.deskrip.setText(dataa.getDescription());
        holder.hargaRS.setText(utils.ConvertRP(dataa.getNominal()));
        holder.saldoRS.setText(utils.ConvertRP(dataa.getBalance()));
        holder.tanggalRS.setText(dataa.getCreated_at().substring(1,10));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView deskrip, hargaRS,saldoRS,tanggalRS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deskrip = itemView.findViewById(R.id.CaptionRS);
            hargaRS = itemView.findViewById(R.id.hargaRS);
            saldoRS = itemView.findViewById(R.id.saldoRS);
            tanggalRS = itemView.findViewById(R.id.tanggalRS);



        }
    }
}
