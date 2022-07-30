package com.c.cpay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpay.CetakStruk.Cetakstruk;
import com.c.cpay.DaftarHarga.DaftarHarga_activity;
import com.c.cpay.Fragment.TransaksiFragment;
import com.c.cpay.KodeProduk.Kodeprodukact;
import com.c.cpay.KomisiSales.Komisi_sales;
import com.c.cpay.MarkUP.markupSpesifik.MarkUpSpesifikActi;
import com.c.cpay.Model.MSubMenu;
import com.c.cpay.Notifikasi.Notifikasi_Activity;
import com.c.cpay.PengajuanLimit.PengajuanDompet;
import com.c.cpay.PersetujuanSaldoSales.PersetujuanSaldoSales;
import com.c.cpay.Profil.Poin.Point;
import com.c.cpay.R;
import com.c.cpay.TagihanKonter.TagihanKonter;
import com.c.cpay.TagihanKonterSales.TagihanKonterbySales;
import com.c.cpay.TambahKonter.addKonter;
import com.c.cpay.TarikKomisi.TarikKomisiSales_Activity;
import com.c.cpay.drawer_activity;
import com.c.cpay.konter.konter_activity;
import com.c.cpay.reseller.PersetujuanSaldokuReseller;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterSubMenuSide extends RecyclerView.Adapter<AdapterSubMenuSide.ViewHolder> {

    Context context;
    private List<MSubMenu> subMenus;
    drawer_activity drawer_activity;

    public AdapterSubMenuSide(Context context, ArrayList<MSubMenu> subMenus, drawer_activity drawer_activity) {
        this.context = context;
        this.subMenus = subMenus;
        this.drawer_activity = drawer_activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmenu_sidebar, parent, false);
        AdapterSubMenuSide.ViewHolder holder = new AdapterSubMenuSide.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MSubMenu mSubMenu = subMenus.get(position);
        holder.namesub.setText(mSubMenu.getName().trim());

        if (mSubMenu.getIcon().equals("")) {
            Picasso.get().load("http").into(holder.iconsub);
        } else {
            Picasso.get().load(mSubMenu.getIcon()).into(holder.iconsub);
        }


        holder.linside.setOnClickListener(v -> {

            switch (mSubMenu.getLink()) {

                case "daftar_harga": {

                    Intent intent = new Intent(context, DaftarHarga_activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "pengajuan_dompet": {

                    Intent intent = new Intent(context, PengajuanDompet.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "poin_reward": {

                    Intent intent = new Intent(context, Point.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "notifikasi": {

                    Intent intent = new Intent(context, Notifikasi_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "cetak_struct": {

                    Intent intent = new Intent(context, Cetakstruk.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "kode_semua_produk": {

                    Intent intent = new Intent(context, Kodeprodukact.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();

                    break;
                }
                case "persetujuan_saldo_server": {

                    Intent intent = new Intent(context, PersetujuanSaldoSales.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();

                    break;
                }
                case "konter_tambah": {

                    Intent intent = new Intent(context, addKonter.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "riwayat_transaksi": {
                    TransaksiFragment fragment1 = new TransaksiFragment();
                    FragmentManager fm3 = drawer_activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction2 = fm3.beginTransaction();
                    fragmentTransaction2.replace(R.id.fLayout, fragment1);
                    fragmentTransaction2.commit(); // save the change
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "mark_up": {

                    Intent intent = new Intent(context, MarkUpSpesifikActi.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "konter_tagihan_pembayaran": {
                    Intent intent = new Intent(context, TagihanKonter.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "konter": {
                    Intent intent = new Intent(context, konter_activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "riwayat_komisi": {
                    Intent intent = new Intent(context, Komisi_sales.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "konter_tagihan": {
                    Intent intent = new Intent(context, TagihanKonterbySales.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "persetujuan_saldoku_reseller": {
                    Intent intent = new Intent(context, PersetujuanSaldokuReseller.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "tarik_komisi": {
                    Intent intent = new Intent(context, TarikKomisiSales_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return subMenus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namesub;
        ImageView iconsub;
        LinearLayout linside;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namesub = itemView.findViewById(R.id.name_list_sub);
            iconsub = itemView.findViewById(R.id.icon_list_sub);
            linside = itemView.findViewById(R.id.Linierside);
        }
    }
}


