package com.c.dompetabata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Fragment.ChatFragment;
import com.c.dompetabata.Fragment.HomeFragment;
import com.c.dompetabata.Fragment.TransaksiFragment;
import com.c.dompetabata.sharePreference.Preference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class drawer_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView menu_bawah;
    Toolbar toolbar;
    TextView tulisan;
    DrawerLayout drawer_layout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Fragment fragment1,fragment2,fragment3;
    MenuItem menuItem;

    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_activity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        menu_bawah = findViewById(R.id.menu_bawah);
        tulisan = findViewById(R.id.tulisan);

        navigationView = findViewById(R.id.nav_view);
        drawer_layout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();



        fragment1 = new TransaksiFragment();
        fragment2 = new ChatFragment();
        fragment3 = new HomeFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fLayout, fragment3);
        fragmentTransaction.commit(); // save the changes
        menu_bawah.setOnNavigationItemSelectedListener(this::onOptionsItemSelected);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {



            case R.id.home:
                // create a FragmentManager
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fLayout, fragment3);
                fragmentTransaction.commit(); // save the changes
                break;

            case R.id.transaksi:

                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction fragmentTransactionn = fm2.beginTransaction();
                fragmentTransactionn.replace(R.id.fLayout, fragment1);
                fragmentTransactionn.commit(); // save the changes

                break;
            case R.id.chat:

                FragmentManager fm3 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fm3.beginTransaction();
                fragmentTransaction2.replace(R.id.fLayout, fragment2);
                fragmentTransaction2.commit(); // save the changes

                break;

        }
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

    public void LinDaftarHarga(View view){
        drawer_layout.closeDrawers();

    }

    public void LinKeluar (View view){



        AlertDialog.Builder alertdialog = new AlertDialog.Builder(drawer_activity.this);
        alertdialog.setTitle("Keluar");
        alertdialog.setMessage("Apakah anda yakin ingin keluar ?");
        alertdialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Preference.getSharedPreference(getApplicationContext());
                Preference.setkredentials(getApplicationContext(),"");
                Preference.setPIN(getApplicationContext(),"");
                Preference.setToken(getApplicationContext(),"");
                finish();
            }
        });

        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });


        AlertDialog alertDialog = alertdialog.create();
        alertDialog.show();

    }


}