package com.c.faizpay.TopUpSaldoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.faizpay.Helper.utils;
import com.c.faizpay.R;
import com.c.faizpay.TopUpSaldoku.MetodeBayar.Qris_Activity;
import com.c.faizpay.TopUpSaldoku.MetodeBayar.VAMethodActivity;
import com.c.faizpay.sharePreference.Preference;
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
        jumlahtopup.setText(utils.ConvertRP(Preference.getSaldoku(getContext()).replace(",", "")));
        TextView metodetopup = v.findViewById(R.id.metodetopup);
        metodetopup.setText(name);


        batal.setOnClickListener(view -> dismiss());

        lanjut.setOnClickListener(view -> {

            String type = getArguments().getString("type");

            if (type.equals("va")) {
                Intent intent = new Intent(getContext(), VAMethodActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title", getArguments().getString("Title"));
                intent.putExtra("NamaRekening", getArguments().getString("NamaRekening"));
                intent.putExtra("NoRekening", getArguments().getString("NoRekening"));
                intent.putExtra("id", getArguments().getString("id"));
                intent.putExtra("name", getArguments().getString("name"));
                intent.putExtra("icon", getArguments().getString("icon"));
                startActivity(intent);

            } else if (type.equals("qris")) {
                Intent intent = new Intent(getContext(), Qris_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title", getArguments().getString("Title"));
                intent.putExtra("NamaRekening", getArguments().getString("NamaRekening"));
                intent.putExtra("NoRekening", getArguments().getString("NoRekening"));
                intent.putExtra("id", getArguments().getString("id"));
                intent.putExtra("name", getArguments().getString("name"));
                intent.putExtra("icon", getArguments().getString("icon"));
                startActivity(intent);

            }  else {
                Intent intent = new Intent(getContext(), TrasferBank.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title", getArguments().getString("Title"));
                intent.putExtra("NamaRekening", getArguments().getString("NamaRekening"));
                intent.putExtra("NoRekening", getArguments().getString("NoRekening"));
                intent.putExtra("id", getArguments().getString("id"));
                startActivity(intent);
            }


            dismiss();


        });

        return v;
    }

}
