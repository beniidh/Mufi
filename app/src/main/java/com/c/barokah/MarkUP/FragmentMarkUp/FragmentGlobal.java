package com.c.barokah.MarkUP.FragmentMarkUp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c.barokah.Api.Api;
import com.c.barokah.Helper.RetroClient;
import com.c.barokah.MarkUP.ModalMarUp;
import com.c.barokah.MarkUP.ResponMarkup;
import com.c.barokah.MarkUP.sendMarkUP;
import com.c.barokah.R;
import com.c.barokah.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentGlobal extends Fragment  {

    EditText markupEditText, markupNilai;
    Button updateharga;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_global, container, false);
        markupNilai = v.findViewById(R.id.markupNilai);
        updateharga = v.findViewById(R.id.markupButton);
        updateharga.setOnClickListener(view -> {

            if(!markupNilai.getText().toString().isEmpty()){
                int nominal = Integer.parseInt(markupNilai.getText().toString());
                MarkUP(nominal);

            }else {

                Toast.makeText(getContext(),"Jumlah tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }



        });

        markupEditText = v.findViewById(R.id.markupEditText);
        markupEditText.setOnClickListener(w -> {
            ModalMarUp modalMarUp = new ModalMarUp();
            modalMarUp.show(getParentFragmentManager(), "ModalMarkUp");

        });

        return v;
    }

    public void MarkUP(int nominal) {
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        sendMarkUP sendMarkUP = new sendMarkUP(nominal);

        Call<ResponMarkup> call = api.markup(token, sendMarkUP);
        call.enqueue(new Callback<ResponMarkup>() {
            @Override
            public void onResponse(Call<ResponMarkup> call, Response<ResponMarkup> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast).show();
//                    finish();
                    markupNilai.setText("");
                } else {
                    StyleableToast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }

            }

            @Override
            public void onFailure(Call<ResponMarkup> call, Throwable t) {

            }
        });

    }
}