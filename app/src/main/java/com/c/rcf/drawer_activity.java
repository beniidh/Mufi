package com.c.rcf;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.rcf.Adapter.AdapterSubMenuSide;
import com.c.rcf.Api.Api;
import com.c.rcf.Fragment.ChatFragment;
import com.c.rcf.Fragment.HomeFragment;
import com.c.rcf.Fragment.HomeViewModel;
import com.c.rcf.Fragment.RekapsaldoFragment.RekapFragment;
import com.c.rcf.Fragment.Respon.MRuningText;
import com.c.rcf.Fragment.TransaksiFragment;
import com.c.rcf.Model.MSubMenu;
import com.c.rcf.Notifikasi.Notifikasi_Activity;
import com.c.rcf.Respon.ResponBanner;
import com.c.rcf.Respon.ResponMenu;
import com.c.rcf.Respon.ResponProfil;
import com.c.rcf.Helper.RetroClient;
import com.c.rcf.Model.MBanner;
import com.c.rcf.Model.Micon;
import com.c.rcf.Profil.Profil;
import com.c.rcf.sharePreference.Preference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class drawer_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView menu_bawah;
    Toolbar toolbar;
    TextView tulisan, nameheadernav, navheadernamakonter;
    DrawerLayout drawer_layout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    Fragment home;

    LinearLayout profil;
    ArrayList<Micon> micons = new ArrayList<>();
    ArrayList<MBanner> mBanners = new ArrayList<>();
    HomeViewModel myViewModel;

    AdapterSubMenuSide adapterSubMenuSide;
    ArrayList<MSubMenu> mSubMenus = new ArrayList<>();
    RecyclerView submenu;
    ImageView iconprofilsidebar;
    ImageBadgeView notifikasi;
    TextView parent;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_activity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        iconprofilsidebar = findViewById(R.id.iconprofilsidebar);
        parent = findViewById(R.id.parent);
        nameheadernav = findViewById(R.id.nameheadernav);

        notifikasi = findViewById(R.id.notifikasiID);


        notifikasi.setOnClickListener(v -> {

            Intent intent = new Intent(drawer_activity.this, Notifikasi_Activity.class);
            startActivity(intent);
            Preference.setNilaiNotif(getApplicationContext(), 0);
            notifikasi.setBadgeValue(0);

        });

        ImageView togglenav = findViewById(R.id.togglenavheader);

        getContentProfil();

        submenu = findViewById(R.id.ReySubMenu);
        adapterSubMenuSide = new AdapterSubMenuSide(getApplicationContext(), mSubMenus, drawer_activity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        submenu.setLayoutManager(mLayoutManager);
        submenu.setAdapter(adapterSubMenuSide);

        menu_bawah = findViewById(R.id.menu_bawah);
        tulisan = findViewById(R.id.tulisan);
        navheadernamakonter = findViewById(R.id.navheadernamakonter);
        getMicons();


        myViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        myViewModel.init();

        profil = findViewById(R.id.LinearProfil);
        profil.setOnClickListener(v -> {

            Intent intent = new Intent(drawer_activity.this, Profil.class);
            startActivity(intent);
        });
        navigationView = findViewById(R.id.nav_view);
        drawer_layout = findViewById(R.id.drawer_layout);
        togglenav.setOnClickListener(v -> drawer_layout.open());
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();


        home = new HomeFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fLayout, home);
        fragmentTransaction.commit(); // save the changes

        menu_bawah.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fLayout, home).commit();
                    return true;
                case R.id.transaksi:
                    Fragment Transaksi = new TransaksiFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fLayout, Transaksi).commit();
                    return true;
                case R.id.rekapSaldo:
                    Fragment rekap = new RekapFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fLayout, rekap).commit();
                    return true;

                case R.id.chat:
                    Fragment chat = new ChatFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fLayout, chat).commit();
                    return true;
            }

            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.daftarharga:
                Toast.makeText(getApplicationContext(), "Daftar Harga", Toast.LENGTH_SHORT).show();
                drawer_layout.closeDrawers();
                break;
        }
        return false;
    }

    public void LinDaftarHarga() {
        drawer_layout.closeDrawers();

    }


    public void LinKeluar(View view) {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(drawer_activity.this);
        alertdialog.setTitle("Keluar");
        alertdialog.setMessage("Apakah anda yakin ingin keluar ?");
        alertdialog.setPositiveButton("yes", (dialog, which) -> {
            Preference.getSharedPreference(getApplicationContext());
            Preference.setkredentials(getApplicationContext(), "");
            Preference.setPIN(getApplicationContext(), "");
            Preference.setToken(getApplicationContext(), "");
            finish();
        });

        alertdialog.setNegativeButton("No", (dialog, which) -> {

        });

        AlertDialog alertDialog = alertdialog.create();
        alertDialog.show();

    }


    public void Kebijakan(View view) {

        String url = "https://www.termsfeed.com/live/fe6cb4ee-b9b6-495b-9508-df7333dc077b";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            Toast.makeText(getApplicationContext(), ex.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {
                navheadernamakonter.setText(response.body().getData().getStore_name());
                nameheadernav.setText(response.body().getData().getStore_name());
                myViewModel.sendPayLater(response.body().getData().getPaylater_status());
                myViewModel.sendSaldoku(response.body().getData().getWallet().getSaldoku());
                myViewModel.sendPayyLetter(response.body().getData().getWallet().getPaylatter());
                parent.setText(response.body().getData().getReferal_code());
                Picasso.get().load(response.body().getData().getAvatar()).into(iconprofilsidebar);
                Preference.setServerID(getApplicationContext(), response.body().getData().getServer_id());
                getIconBanner(response.body().getData().getServer_id());
                Preference.setKeyUserCode(getApplicationContext(), response.body().getData().getCode());
                mSubMenus = (ArrayList<MSubMenu>) response.body().getData().getMenu();
                getRunningText(response.body().getData().getServer_id());
                adapterSubMenuSide = new AdapterSubMenuSide(getApplicationContext(), mSubMenus, drawer_activity.this);
                submenu.setAdapter(adapterSubMenuSide);

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    public ArrayList<Micon> getMicons() {
        Api api = RetroClient.getApiServices();
        Call<ResponMenu> call = api.getAllProduct("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponMenu>() {
            @Override
            public void onResponse(Call<ResponMenu> call, Response<ResponMenu> response) {
                String code = response.body().getCode();

                if (code.equals("200")) {

                    micons = (ArrayList<Micon>) response.body().getData();
                    myViewModel.sendDataIcon(micons);

                }
            }

            @Override
            public void onFailure(Call<ResponMenu> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

        return micons;
    }

    public ArrayList<MBanner> getIconBanner(String id) {

        Api api = RetroClient.getApiServices();
        Call<ResponBanner> call = api.getBanner("Bearer " + Preference.getToken(getApplicationContext()), id);
        call.enqueue(new Callback<ResponBanner>() {
            @Override
            public void onResponse(@NonNull Call<ResponBanner> call, Response<ResponBanner> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    mBanners = response.body().getData();
                    myViewModel.sendDataIconBanner(mBanners);

                }

            }

            @Override
            public void onFailure(Call<ResponBanner> call, @NonNull Throwable t) {

            }
        });
        return mBanners;

    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentProfil();


        notifikasi.setBadgeValue(Preference.getNilaiNotif(getApplicationContext()))
                .setBadgeTextSize(9)
                .setMaxBadgeValue(999)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

    }

    private void getRunningText(String id) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<MRuningText> call = api.getRunningText(token, id);
        call.enqueue(new Callback<MRuningText>() {
            @Override
            public void onResponse(Call<MRuningText> call, Response<MRuningText> response) {

                String code = response.body().getCode();
                if (code.equals("200")) {
                    myViewModel.sendRunning(response.body().getData().get(0).getText_apk());

                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MRuningText> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


}