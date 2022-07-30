package com.c.cpay.Fragment;

import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpay.Adapter.AdapterMenuUtama;
import com.c.cpay.Adapter.SliderAdapter;
import com.c.cpay.Api.Api;
import com.c.cpay.Fragment.DirectLink.AdapterDirectLink;
import com.c.cpay.Fragment.DirectLink.mDirect;
import com.c.cpay.Fragment.RiwayatTransaksi.ModalQris;
import com.c.cpay.Helper.RetroClient;
import com.c.cpay.Helper.utils;
import com.c.cpay.Model.MBanner;
import com.c.cpay.Model.ModelMenuUtama;
import com.c.cpay.Model.SliderItem;
import com.c.cpay.R;
import com.c.cpay.Respon.ResponMenuUtama;
import com.c.cpay.SaldoServer.TopupSaldoServer;
import com.c.cpay.drawer_activity;
import com.c.cpay.sharePreference.Preference;
import com.c.cpay.TopUpSaldoku.topup_saldoku_activity;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    TextView saldoku, marqueeText;
    LinearLayout linsaldoserver;
    SliderView sliderView;
    AdapterMenuUtama adapterMenuUtama;
    AdapterDirectLink adapterDirectLink;
    RecyclerView reymenu, reydirectmenu, reymenuPasca;
    ImageView qris, KlikSaldoku;
    ArrayList<ModelMenuUtama> menuUtamas = new ArrayList<>();
    ArrayList<ModelMenuUtama> menuUtamasP = new ArrayList<>();
    ArrayList<ModelMenuUtama> menuUtamasPRA = new ArrayList<>();
    ArrayList<mDirect.Data> menuDirect = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        reymenu = v.findViewById(R.id.ReyMenuUtama);
        reymenuPasca = v.findViewById(R.id.ReyMenuUtamaPasca);
        reydirectmenu = v.findViewById(R.id.ReyMenuDirect);
        getAllmenu();
        getDirectLink();
//        getContentProfil();

        int numberOfColumns = 5;
        reymenu.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns, GridLayoutManager.VERTICAL, false));

        reymenuPasca.setLayoutManager(new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false));

        reydirectmenu.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns, GridLayoutManager.VERTICAL, false));
        adapterDirectLink = new AdapterDirectLink(getActivity(), menuDirect);
        reydirectmenu.setAdapter(adapterDirectLink);
        marqueeText = v.findViewById(R.id.marqueeText);
        marqueeText.setSelected(true);

        sliderView = v.findViewById(R.id.imageSlider);
        KlikSaldoku = v.findViewById(R.id.imageView3);
        qris = v.findViewById(R.id.imageView2);
        qris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ModalQris modalQris = new ModalQris();
                modalQris.show(getChildFragmentManager(), "qris");
            }
        });

        saldoku = v.findViewById(R.id.saldoku);
//        saldoserver = v.findViewById(R.id.saldoserver);

        KlikSaldoku.setOnClickListener(v1 -> {

            if (!Preference.getServerID(getContext()).equals("SRVID00000002")) {
                Intent intent = new Intent(getActivity(), topup_saldoku_activity.class);
                intent.putExtra("saldoku", saldoku.getText().toString());
                startActivity(intent);

            }

        });

        linsaldoserver = v.findViewById(R.id.kliksaldoserver);

        SwipeRefreshLayout swipelainnya = v.findViewById(R.id.swipehome);
        swipelainnya.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllmenu();
                swipelainnya.setRefreshing(false);
                ((drawer_activity) getActivity()).getContentProfil();
                getDirectLink();
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getIconBanner().observe(getViewLifecycleOwner(), new Observer<ArrayList<MBanner>>() {
            @Override
            public void onChanged(ArrayList<MBanner> mBanners) {
                SliderAdapter adapter = new SliderAdapter(getContext());
                for (int i = 0; i < mBanners.size(); i++) {
                    adapter.addItem(new SliderItem(mBanners.get(i).getDescription(), mBanners.get(i).getImage()));

                }
                sliderView.setSliderAdapter(adapter);
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                sliderView.setIndicatorSelectedColor(Color.WHITE);
                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                sliderView.setScrollTimeInSec(5);
                sliderView.setAutoCycle(true);

                sliderView.startAutoCycle();
            }
        });


        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getRunning().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.isEmpty()) {
                    marqueeText.setText("Selamat datang di Aplikasi K Reload, maksimalkan transaksi anda");
                } else {
                    marqueeText.setText(s);
                }

            }
        });

        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getSaldoku().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                saldoku.setText(utils.ConvertRP(s));

            }
        });

    }

    public void getAllmenu() {

        Api api = RetroClient.getApiServices();
        Call<ResponMenuUtama> call = api.getAllMenu("Bearer " + Preference.getToken(getActivity()));
        call.enqueue(new Callback<ResponMenuUtama>() {
            @Override
            public void onResponse(Call<ResponMenuUtama> call, Response<ResponMenuUtama> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    menuUtamas = response.body().getData();
                    menuUtamasP.clear();
                    menuUtamasPRA.clear();
                    for (ModelMenuUtama menuUtama : menuUtamas) {

                        if (menuUtama.getType().equals("PASCABAYAR")) {
                            menuUtamasP.add(menuUtama);
                            adapterMenuUtama = new AdapterMenuUtama(getContext(), menuUtamasP);
                            reymenuPasca.setAdapter(adapterMenuUtama);
                        } else {
                            menuUtamasPRA.add(menuUtama);
                            adapterMenuUtama = new AdapterMenuUtama(getContext(), menuUtamasPRA);
                            reymenu.setAdapter(adapterMenuUtama);
                        }

                    }


                }

            }

            @Override
            public void onFailure(Call<ResponMenuUtama> call, Throwable t) {

            }
        });

    }

    private void getDirectLink() {
        Api api = RetroClient.getApiServices();
        Call<mDirect> call = api.getDirectMenu("Bearer " + Preference.getToken(getActivity()));
        call.enqueue(new Callback<mDirect>() {
            @Override
            public void onResponse(Call<mDirect> call, Response<mDirect> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    menuDirect = response.body().getData();
                    adapterDirectLink = new AdapterDirectLink(getActivity(), menuDirect);
                    reydirectmenu.setAdapter(adapterDirectLink);

                }

            }

            @Override
            public void onFailure(Call<mDirect> call, Throwable t) {

            }
        });

    }

}


