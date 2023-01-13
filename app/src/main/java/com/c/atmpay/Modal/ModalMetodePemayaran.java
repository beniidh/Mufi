package com.c.atmpay.Modal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.c.atmpay.R;
import com.c.atmpay.SaldoServer.BayarSalesServer;
import com.c.atmpay.TopUpSaldoku.BayarSales;
import com.c.atmpay.Transaksi.BayarViaBank;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalMetodePemayaran extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_metode_pebayaran, container, false);

        LinearLayout linearSales = v.findViewById(R.id.LinearBayarSales);
        linearSales.setOnClickListener(v1 -> {
            String codebayar = getArguments().getString("saldotipe");

            if (codebayar.equals("saldoserver")) {
                Intent intent = new Intent(getActivity(), BayarSalesServer.class);
                startActivity(intent);
                dismiss();

            } else {
                Intent intent = new Intent(getActivity(), BayarSales.class);
                startActivity(intent);
                dismiss();
            }


        });

        LinearLayout LinearBayarBank = v.findViewById(R.id.LinearBayarBank);

        LinearBayarBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codebayar = getArguments().getString("saldotipe");
                Intent intent = new Intent(getActivity(), BayarViaBank.class);
                intent.putExtra("saldotipe",codebayar);
                startActivity(intent);
                dismiss();

            }
        });

        return v;
    }

}