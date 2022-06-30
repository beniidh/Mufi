package com.c.kreload.menuUtama.HolderPulsa;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.kreload.Helper.utils;
import com.c.kreload.R;
import com.c.kreload.menuUtama.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.kreload.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DetailTransaksiDigipos extends BottomSheetDialogFragment {

    TextView nomor,produk,jenis,harga;
    Button beli,tutup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_detail_digipos, container, false);

        nomor =v.findViewById(R.id.nomorD);
        produk =v.findViewById(R.id.ProdukD);
        jenis =v.findViewById(R.id.JenisD);
        harga =v.findViewById(R.id.hargaD);

        beli =v.findViewById(R.id.beli);
        tutup =v.findViewById(R.id.tutupdetaill);

        beli.setOnClickListener(v12 -> {

            String urllicon = getArguments().getString("urlicon");
            Intent intent = new Intent(getContext(), KonfirmasiPembayaran.class);
            intent.putExtra("hargga", harga.getText().toString());
            intent.putExtra("urll", urllicon);
            Preference.setUrlIcon(getContext(),urllicon);
            intent.putExtra("RefID", getArguments().getString("RefID"));
            intent.putExtra("sku_code",getArguments().getString("sku_code"));
            intent.putExtra("inquiry",getArguments().getString("inquiry"));
            intent.putExtra("nomorr",getArguments().getString("nomorr"));
            startActivity(intent);
            dismiss();

        });

        tutup.setOnClickListener(v1 -> {
            dismiss();
        });




        String nomorr = getArguments().getString("nomorr");
        String hargaa = getArguments().getString("hargga");
        String nama = getArguments().getString("namecustomer");



        nomor.setText(nomorr);
        produk.setText(nama);
        harga.setText(utils.ConvertRP(hargaa));
        jenis.setText(Preference.getJenisProduk(getContext()));
        return v;
    }
}
