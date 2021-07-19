package com.c.dompetabata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView menu_bawah;
    TextView tulisan;
    private ActionBarDrawerToggle burger;
    private DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Home <b></font>"));

        menu_bawah = findViewById(R.id.menu_bawah);
        tulisan = findViewById(R.id.tulisan);
        dl = findViewById(R.id.drawer_layout);
        burger = new ActionBarDrawerToggle(this,dl,R.string.open,R.string.close);
        burger.setDrawerIndicatorEnabled(true);
//        dl.addDrawerListener(burger);
//        burger.syncState();
        menu_bawah.setOnNavigationItemSelectedListener(this::onOptionsItemSelected);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
  switch (item.getItemId()){
      case R.id.home :
          tulisan.setText("Home");
          break;
      case R.id.transaksi :
          tulisan.setText("Transaksi");
          break;
      case R.id.chat :
          tulisan.setText("Chat");

  }
        return burger.onOptionsItemSelected(item) || true;
    }

}