package com.c.kreload.Notifikasi.Pesan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c.kreload.Api.Api;
import com.c.kreload.Helper.RetroClient;
import com.c.kreload.R;
import com.c.kreload.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPesan extends Fragment {

    RecyclerView recyclerView;
    AdapterFragmentPesan adapterFragmentPesan;
    ArrayList<mPesan.mData> data = new ArrayList<>();

    public static FragmentPesan newInstance() {
        return new FragmentPesan();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_pesan_fragment, container, false);

        recyclerView = v.findViewById(R.id.reyPesanTransaksi);

        adapterFragmentPesan = new AdapterFragmentPesan(getContext(), data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterFragmentPesan);
        getData(Preference.getServerID(getContext()));

        return v;
    }

    public void getData(String id){

        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<mPesan> call = api.getHistoriPesanN(token,id);
        call.enqueue(new Callback<mPesan>() {
            @Override
            public void onResponse(Call<mPesan> call, Response<mPesan> response) {
                String code = response.body().getCode();
                if(code.equals("200")){
                    data = response.body().getData();
                    adapterFragmentPesan = new AdapterFragmentPesan(getContext(),data);
                    recyclerView.setAdapter(adapterFragmentPesan);
                }

            }
            @Override
            public void onFailure(Call<mPesan> call, Throwable t) {

            }
        });

    }

}