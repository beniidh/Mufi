package com.c.atmpay.menuUtama.air;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.atmpay.Api.Api;
import com.c.atmpay.Api.Value;
import com.c.atmpay.Helper.GpsTracker;
import com.c.atmpay.Helper.LoadingPrimer;
import com.c.atmpay.Helper.RetroClient;
import com.c.atmpay.Helper.utils;
import com.c.atmpay.R;
import com.c.atmpay.Transaksi.MInquiry;
import com.c.atmpay.Transaksi.ResponInquiry;
import com.c.atmpay.menuUtama.TransaksiHandle.DetailTransaksiPasca;
import com.c.atmpay.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProdukAir extends RecyclerView.Adapter<AdapterProdukAir.ViewHolder> {

    Context context;
    ArrayList<ResponProdukAir.VoucherData> mVoucherProduk;
    String nomor, type;
    produkair Produkair;

    public AdapterProdukAir(produkair Produkair,Context context, ArrayList<ResponProdukAir.VoucherData> mVoucherProduk, String nomor, String type) {
        this.context = context;
        this.mVoucherProduk = mVoucherProduk;
        this.nomor = nomor;
        this.type = type;
        this.Produkair = Produkair;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_air, parent, false);
        AdapterProdukAir.ViewHolder holder = new AdapterProdukAir.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponProdukAir.VoucherData mVoucherData = mVoucherProduk.get(position);
        holder.name.setText(mVoucherData.getName());
        holder.deskripsi.setText(mVoucherData.getDescription());
        holder.brand.setText(mVoucherData.getBrand());

        if (mVoucherData.isGangguan()) {
            holder.gangguan.setVisibility(View.VISIBLE);
            holder.linearklik.setEnabled(false);
        }else {
            holder.gangguan.setVisibility(View.GONE);
            holder.linearklik.setEnabled(true);
        }
        holder.linearklik.setOnClickListener(v -> {
            LoadingPrimer loadingPrimer = new LoadingPrimer(Produkair);
            loadingPrimer.startDialogLoading();

            GpsTracker gpsTracker = new GpsTracker(context);

            Api api = RetroClient.getApiServices();
            MInquiry mInquiry = new MInquiry(mVoucherData.getCode(), nomor, type, Value.getMacAddress(context), Value.getIPaddress(), Value.getUserAgent(context), gpsTracker.getLatitude(), gpsTracker.getLongitude());
            String token = "Bearer " + Preference.getToken(context);
            Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
            call.enqueue(new Callback<ResponInquiry>() {
                @Override
                public void onResponse(Call<ResponInquiry> call, Response<ResponInquiry> response) {

                    String code = response.body().getCode();
                    if (code.equals("200")) {

                        if (response.body().getData().getStatus().equals("Sukses")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("nomorr", nomor);
                            bundle.putString("namecustomer", response.body().getData().getCustomer_name());
                            bundle.putString("RefID", response.body().getData().getRef_id());
                            bundle.putString("sku_code", response.body().getData().getBuyer_sku_code());
                            bundle.putString("kodeproduk", "pulsapasca");
                            bundle.putString("inquiry", response.body().getData().getInquiry_type());
                            bundle.putString("hargga", response.body().getData().getSelling_price());

                            bundle.putString("status", response.body().getData().getStatus());
                            bundle.putString("tagihan", utils.ConvertRP(response.body().getData().getDetail_product().getDetail().get(0).getNilai_tagihan()));
                            bundle.putString("deskription", response.body().getData().getDescription());
                            bundle.putString("admin", utils.ConvertRP(response.body().getData().getDetail_product().getDetail().get(0).getAdmin()));

                            Preference.setUrlIcon(context, "");
                            DetailTransaksiPasca fragment = new DetailTransaksiPasca(); // you fragment
                            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                            fragment.setArguments(bundle);
                            loadingPrimer.dismissDialog();
                            fragment.show(fragmentManager, "detail");
                        } else {
                            loadingPrimer.dismissDialog();
                            Toast.makeText(context, response.body().getData().getStatus() + " " + response.body().getData().getDescription(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        loadingPrimer.dismissDialog();
                        Toast.makeText(context, response.body().getError(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponInquiry> call, Throwable t) {
                    Toast.makeText(context,"Koneksi Tidak stabil,silahkan ulangi",Toast.LENGTH_SHORT).show();
                    loadingPrimer.dismissDialog();
                }
            });
        });


    }

    @Override
    public int getItemCount() {
        return mVoucherProduk.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, deskripsi, brand,gangguan;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameair);
            deskripsi = itemView.findViewById(R.id.deskriair);
            linearklik = itemView.findViewById(R.id.linearklikair);
            brand = itemView.findViewById(R.id.brandair);
            gangguan = itemView.findViewById(R.id.gangguan);

        }
    }
}
