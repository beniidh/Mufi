package com.c.barokah.Fragment;

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

import com.c.barokah.Adapter.AdapterMenuUtama;
import com.c.barokah.Adapter.SliderAdapter;
import com.c.barokah.Api.Api;
import com.c.barokah.Fragment.DirectLink.AdapterDirectLink;
import com.c.barokah.Fragment.DirectLink.mDirect;
import com.c.barokah.Helper.RetroClient;
import com.c.barokah.Helper.utils;
import com.c.barokah.Model.MBanner;
import com.c.barokah.Model.ModelMenuUtama;
import com.c.barokah.Model.SliderItem;
import com.c.barokah.R;
import com.c.barokah.Respon.ResponMenuUtama;
import com.c.barokah.SaldoServer.TopupSaldoServer;
import com.c.barokah.drawer_activity;
import com.c.barokah.menuUtama.DirectLink.AdapterMenuDirect;
import com.c.barokah.menuUtama.DirectLink.mDirectL;
import com.c.barokah.sharePreference.Preference;
import com.c.barokah.TopUpSaldoku.topup_saldoku_activity;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    TextView saldoku, marqueeText,saldoserver,captionlainnya;
    LinearLayout linsaldoserver,KlikSaldoku;
    SliderView sliderView;
    AdapterMenuUtama adapterMenuUtama;
    AdapterDirectLink adapterDirectLink;
    AdapterMenuDirect adapterMenuDirect;
    RecyclerView reymenu, reydirectmenu, reymenuPasca,reyDirect;
    ImageView qris;
    ArrayList<ModelMenuUtama> menuUtamas = new ArrayList<>();
    ArrayList<ModelMenuUtama> menuUtamasP = new ArrayList<>();
    ArrayList<ModelMenuUtama> menuUtamasPRA = new ArrayList<>();
    ArrayList<mDirect.Data> menuDirect = new ArrayList<>();
    ArrayList<mDirectL.mData> directLS = new ArrayList<>();
    ArrayList<mDirectL.mData> directLC = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        reymenu = v.findViewById(R.id.ReyMenuUtama);
        reymenuPasca = v.findViewById(R.id.ReyMenuUtamaPasca);
        reydirectmenu = v.findViewById(R.id.ReyMenuDirect);
        reyDirect = v.findViewById(R.id.reyLainnya);
        saldoserver = v.findViewById(R.id.saldoserver);
        captionlainnya = v.findViewById(R.id.CaptionLainnya);

        getAllmenu();
        getDirectLink();
//        getContentProfil();

        int numberOfColumns = 5;
        reymenu.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns, GridLayoutManager.VERTICAL, false));
        reymenuPasca.setLayoutManager(new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false));
        reydirectmenu.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns, GridLayoutManager.VERTICAL, false));
        adapterDirectLink = new AdapterDirectLink(getActivity(), menuDirect);
        reydirectmenu.setAdapter(adapterDirectLink);

        reyDirect.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns, GridLayoutManager.VERTICAL, false));

        marqueeText = v.findViewById(R.id.marqueeText);
        marqueeText.setSelected(true);

        sliderView = v.findViewById(R.id.imageSlider);
        KlikSaldoku = v.findViewById(R.id.KlikSaldoku);

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

        linsaldoserver.setOnClickListener(view -> {

            if(!Preference.getServerID(getContext()).equals("SRVID00000002")) {
                Intent intent = new Intent(getActivity(), TopupSaldoServer.class);
                intent.putExtra("saldoku", saldoku.getText().toString());
                startActivity(intent);
            }

        });


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
                    marqueeText.setText("Selamat datang di Aplikasi Barokah Reload, maksimalkan transaksi anda");
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

        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getPayyLetter().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                saldoserver.setText(utils.ConvertRP(s));

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
        Call<mDirectL> call = api.getDirect("Bearer " + Preference.getToken(getActivity()));
        call.enqueue(new Callback<mDirectL>() {
            @Override
            public void onResponse(Call<mDirectL> call, Response<mDirectL> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    directLS.clear();
                   for (mDirectL.mData data : response.body().getData()){

                       if(data.getStatus().equals("1")){

                           directLS.add(data);
                       }

                   }


                    adapterMenuDirect = new AdapterMenuDirect(getActivity(), directLS);
                    reyDirect.setAdapter(adapterMenuDirect);

                }else {
                    captionlainnya.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<mDirectL> call, Throwable t) {

            }
        });

    }

}


