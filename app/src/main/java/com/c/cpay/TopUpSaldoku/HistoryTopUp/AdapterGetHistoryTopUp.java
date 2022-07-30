package com.c.cpay.TopUpSaldoku.HistoryTopUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpay.Helper.utils;
import com.c.cpay.R;

import java.util.ArrayList;

public class AdapterGetHistoryTopUp extends RecyclerView.Adapter<AdapterGetHistoryTopUp.ViewHolder> {
    Context context;
    ArrayList<ResponHistory.mData> mData;

    public AdapterGetHistoryTopUp(Context context, ArrayList<ResponHistory.mData> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history_topup, parent, false);
        AdapterGetHistoryTopUp.ViewHolder holder = new AdapterGetHistoryTopUp.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponHistory.mData data = mData.get(position);
        holder.harga.setText(utils.ConvertRP(data.getAmount()));
        holder.status.setText(data.getStatus());
        holder.tanggal.setText(data.getCreated_at().substring(0,10));

        if(!data.getProof_payment().isEmpty()){
            holder.bukti.setText("Sudah upload bukti");
        }else {
            holder.bukti.setText("Belum upload bukti");
            holder.upload.setVisibility(View.VISIBLE);
        }

        if(!data.getStatus().equals("PENDING")){
            holder.upload.setVisibility(View.GONE);
            holder.cancel.setVisibility(View.GONE);
        }else {
            holder.upload.setVisibility(View.VISIBLE);
            holder.cancel.setVisibility(View.VISIBLE);

            if(!data.getProof_payment().isEmpty()){
                holder.upload.setVisibility(View.GONE);
            }else {
                holder.upload.setVisibility(View.VISIBLE);
            }

        }

        holder.cancel.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalCancel modalCancel = new ModalCancel();
            FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
            bundle.putString("id",data.getId());
            modalCancel.setArguments(bundle);
            modalCancel.show(fragmentManager,"cancel");

        });

        holder.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,uploadBukti.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",data.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView harga, tanggal, status, bukti;
        LinearLayout linearklik;
        Button upload,cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            harga = itemView.findViewById(R.id.harga);
            tanggal = itemView.findViewById(R.id.tanggal);
            status = itemView.findViewById(R.id.status);
            bukti = itemView.findViewById(R.id.bukti);
            upload = itemView.findViewById(R.id.upload);
            cancel = itemView.findViewById(R.id.cancel);

        }
    }
}
