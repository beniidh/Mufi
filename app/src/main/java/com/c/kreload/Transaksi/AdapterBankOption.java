package com.c.kreload.Transaksi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.kreload.R;
import com.c.kreload.SaldoServer.TrasferBankServer;
import com.c.kreload.TopUpSaldoku.TrasferBank;
import com.c.kreload.Transfer.ModelKonter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterBankOption extends RecyclerView.Adapter<AdapterBankOption.ViewHolder> {

    private final Context context;
    private List<mBankOption.Data> mBankOptions;
    String codebayar ;

    public AdapterBankOption(Context context, List<mBankOption.Data> mBankOptions, String codebayar) {
        this.context = context;
        this.mBankOptions = mBankOptions;
        this.codebayar = codebayar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listbankoptions, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        mBankOption.Data data = mBankOptions.get(position);
        holder.bankName.setText(data.getName());

        if(!data.getIcon().isEmpty()){
            Picasso.get().load(data.getIcon()).into(holder.icon);
        }else { Picasso.get().load("http://").into(holder.icon);}


        holder.LinearBank.setOnClickListener(v -> {

            if(codebayar.equals("saldoserver")){
                Intent intent = new Intent(context, TrasferBankServer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title",data.getName());
                intent.putExtra("NamaRekening",data.getAccount_name());
                intent.putExtra("NoRekening",data.getAccount_no());
                context.startActivity(intent);

            }else {

                Intent intent = new Intent(context, TrasferBank.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title",data.getName());
                intent.putExtra("NamaRekening",data.getAccount_name());
                intent.putExtra("NoRekening",data.getAccount_no());
                context.startActivity(intent);
            }


        });

    }

    @Override
    public int getItemCount() {
        return mBankOptions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
     TextView bankName;
     ImageView icon;
     LinearLayout LinearBank;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bankName = itemView.findViewById(R.id.bankName);
            LinearBank = itemView.findViewById(R.id.LinearBank);
            icon = itemView.findViewById(R.id.iconBank);


        }
    }


}
