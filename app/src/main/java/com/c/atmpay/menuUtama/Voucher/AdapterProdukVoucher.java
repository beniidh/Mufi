package com.c.atmpay.menuUtama.Voucher;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.atmpay.Api.Api;
import com.c.atmpay.Api.Value;
import com.c.atmpay.Helper.GpsTracker;
import com.c.atmpay.Helper.RetroClient;
import com.c.atmpay.Helper.utils;
import com.c.atmpay.R;
import com.c.atmpay.Transaksi.MInquiry;
import com.c.atmpay.Transaksi.ResponInquiry;
import com.c.atmpay.menuUtama.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.atmpay.menuUtama.VoucherGame.ModalVoucherGame;
import com.c.atmpay.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProdukVoucher extends RecyclerView.Adapter<AdapterProdukVoucher.ViewHolder> {

    Context context;
    ArrayList<ResponProdukVoucherv.VoucherData> mData;
    private int selectedPosition = 0;
    public static   String nameid[][] = new String[1][2];
    ModalVoucherGame voucher;
    String nomor,urll;
    private ArrayList<Integer> selectCheck = new ArrayList<>();
    public AdapterProdukVoucher(Context context, ArrayList<ResponProdukVoucherv.VoucherData> mData, String nomor, String urll) {
        this.context = context;
        this.mData = mData;
        this.nomor = nomor;
        this.urll = urll;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_pulsapraayar, parent, false);
        AdapterProdukVoucher.ViewHolder holder = new AdapterProdukVoucher.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponProdukVoucherv.VoucherData mdata = mData.get(position);
        holder.name.setText(mdata.getName());
        holder.deskripsi.setText(mdata.getDescription());
        holder.harga.setText(utils.ConvertRP(mdata.getTotal_price()));
        if (mdata.isGangguan()) {
            holder.gangguan.setVisibility(View.VISIBLE);
            holder.linearklik.setEnabled(false);
        }else {
            holder.gangguan.setVisibility(View.GONE);
            holder.linearklik.setEnabled(true);
        }


        holder.linearklik.setOnClickListener(v -> {

            GpsTracker gpsTracker = new GpsTracker(context);
            Api api = RetroClient.getApiServices();
            MInquiry mInquiry = new MInquiry(mdata.getCode(), nomor, "PRABAYAR", Value.getMacAddress(context), Value.getIPaddress(), Value.getUserAgent(context), gpsTracker.getLatitude(), gpsTracker.getLongitude());
            String token = "Bearer " + Preference.getToken(context);

            Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);

            call.enqueue(new Callback<ResponInquiry>() {
                @Override
                public void onResponse(Call<ResponInquiry> call, Response<ResponInquiry> response) {

                    String code = response.body().getCode();

                    if (code.equals("200")) {
                        Intent intent = new Intent(context, KonfirmasiPembayaran.class);
                        intent.putExtra("deskripsi", response.body().getData().getDescription());
                        intent.putExtra("nomorr", Preference.getNo(context));
                        intent.putExtra("kodeproduk", "pulsapra");
                        //transaksi
                        intent.putExtra("RefID",response.body().getData().getRef_id());
                        intent.putExtra("sku_code",response.body().getData().getBuyer_sku_code());
                        intent.putExtra("inquiry",response.body().getData().getInquiry_type());
                        intent.putExtra("hargga",utils.ConvertRP(response.body().getData().getSelling_price()) );
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {

                        Toast.makeText(context, response.body().getError(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponInquiry> call, Throwable t) {

                }
            });


        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, deskripsi, harga,gangguan;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namepulsaprabayar);
            deskripsi = itemView.findViewById(R.id.deskripsiprabayar);
            harga = itemView.findViewById(R.id.hargapulsaprabayar);
            linearklik = itemView.findViewById(R.id.linearklik);
            gangguan = itemView.findViewById(R.id.gangguan);
        }
    }

    public static String[][] getNameid() {
        return nameid;
    }
}
