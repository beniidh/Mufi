package com.c.cpay.TopUpSaldoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpay.Helper.utils;
import com.c.cpay.R;
import com.c.cpay.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalKonfirmasiTopup extends BottomSheetDialogFragment {

    String name;
    public ModalKonfirmasiTopup(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_konfirmasi_bayar, container, false);
        Button lanjut = v.findViewById(R.id.lanjutTopup);
        Button batal = v.findViewById(R.id.batalTopup);

        TextView jumlahtopup = v.findViewById(R.id.jumlahtopup);
        jumlahtopup.setText(utils.ConvertRP(Preference.getSaldoku(getContext()).replace(",","")));
        TextView metodetopup = v.findViewById(R.id.metodetopup);
        metodetopup.setText(name);


        batal.setOnClickListener(view -> dismiss());

        lanjut.setOnClickListener(view -> {

            Intent intent = new Intent(getContext(),TrasferBank.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Title",getArguments().getString("Title"));
            intent.putExtra("NamaRekening",getArguments().getString("NamaRekening"));
            intent.putExtra("NoRekening",getArguments().getString("NoRekening"));
            startActivity(intent);
            dismiss();


        });

        return v;
    }

}
