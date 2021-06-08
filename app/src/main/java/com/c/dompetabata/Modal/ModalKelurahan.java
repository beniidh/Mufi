package com.c.dompetabata.Modal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Adapter.AdapterKabupaten;
import com.c.dompetabata.Adapter.AdapterKelurahan;
import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.ResponK;
import com.c.dompetabata.Helper.Responkel;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Model.ModelKabupaten;
import com.c.dompetabata.Model.ModelKelurahan;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalKelurahan extends BottomSheetDialogFragment {


    RecyclerView recyclerViewKel;
    AdapterKelurahan adapterKelurahan;
    ArrayList<ModelKelurahan> modelKelurahans = new ArrayList<>();
    Button tutup, pilih;
    private BottomSheetListenerKelurahan bottomSheetListenerKelurahan;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_kelurahan, container, false);

        recyclerViewKel = v.findViewById(R.id.ReyKelurahan);
        tutup = v.findViewById(R.id.tutupKel);
        pilih = v.findViewById(R.id.pilihKel);
        searchView = v.findViewById(R.id.search_kelurahan);


        adapterKelurahan = new AdapterKelurahan(getContext(), modelKelurahans);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewKel.setLayoutManager(mLayoutManager);
        recyclerViewKel.setAdapter(adapterKelurahan);
        getKelurahan();

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterKelurahan.getFilter().filter(newText);
                return false;
            }
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id =  Preference.getID(getContext());
                String name = Preference.getName(getContext());

                bottomSheetListenerKelurahan.onButtonClickKelurahan(name, id);
                dismiss();
            }
        });

        return v;
    }

    private void getKelurahan() {

        long id = Long.valueOf(Preference.getIDKecamatan(getContext()));

        Api api = RetroClient.getApiServices();
        Call<Responkel> call = api.getAllKelurahan(id);
        call.enqueue(new Callback<Responkel>() {
            @Override
            public void onResponse(Call<Responkel> call, Response<Responkel> response) {

                modelKelurahans = (ArrayList<ModelKelurahan>) response.body().getData();
                adapterKelurahan = new AdapterKelurahan(getContext(), modelKelurahans);
                recyclerViewKel.setAdapter(adapterKelurahan);
            }

            @Override
            public void onFailure(Call<Responkel> call, Throwable t) {

            }


        });

    }

    public interface BottomSheetListenerKelurahan {
        void onButtonClickKelurahan(String name, String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListenerKelurahan = (BottomSheetListenerKelurahan) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }

}
