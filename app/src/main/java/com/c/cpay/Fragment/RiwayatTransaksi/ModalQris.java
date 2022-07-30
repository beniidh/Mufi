package com.c.cpay.Fragment.RiwayatTransaksi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.c.cpay.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalQris extends BottomSheetDialogFragment {
    Button mengerti;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qris_pengumuman, container, false);

        mengerti = v.findViewById(R.id.mengerti);
        mengerti.setOnClickListener(view -> dismiss());

        return v;

    }



}
