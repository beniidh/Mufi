package com.c.atmpay.Adapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c.atmpay.Model.ModelMenuUtama;
import com.c.atmpay.TransferBank.transferBank;
import com.c.atmpay.menuUtama.BPJS.produkBPJS;
import com.c.atmpay.menuUtama.HolderPulsa.holder_pulsa_activity;
import com.c.atmpay.menuUtama.ListrikPLN.groupPLN;
import com.c.atmpay.menuUtama.ListrikPLNPasca.Pln_produk_pasca;
import com.c.atmpay.menuUtama.PulsaPascaBayar.PulsaPascaBayar_activity;
import com.c.atmpay.R;
import com.c.atmpay.homelainnya;
import com.c.atmpay.menuUtama.WarungAbata.produkWarungAbata;
import com.c.atmpay.sharePreference.Preference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMenuUtama extends RecyclerView.Adapter<AdapterMenuUtama.ViewHolder> {

    Context context;
    ArrayList<ModelMenuUtama> menuUtamas;

    public AdapterMenuUtama(Context context, ArrayList<ModelMenuUtama> menuUtamas) {
        this.context = context;
        this.menuUtamas = menuUtamas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmenu_utama, parent, false);
        AdapterMenuUtama.ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        for (int i =0; i < menuUtamas.size(); i++) {

            if (Preference.getServerID(context).equals("SRVID00000002") && menuUtamas.get(i).getName().equals("Voucher Game")) {

                ModelMenuUtama modelMenuUtamaa = new ModelMenuUtama("halo","halo","halo");

                menuUtamas.set(i,modelMenuUtamaa);
            }
        }

        ModelMenuUtama modelMenuUtama = menuUtamas.get(position);
            Picasso.get().load(modelMenuUtama.getIcon()).into(holder.iconmenu);

        holder.titlemenu.setText(modelMenuUtama.getName());

        holder.linlistmenu.setOnClickListener(v -> {

            switch (modelMenuUtama.getUrl()) {
                case "pulsa_prabayar": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "lainnya": {
                    Intent intent = new Intent(context, homelainnya.class);
                    context.startActivity(intent);
                    break;
                }
                case "paket_data": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "pulsa_pascabayar": {
                    Intent intent = new Intent(context, PulsaPascaBayar_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "pln_prabayar": {
                    Intent intent = new Intent(context, groupPLN.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "pln_pascabayar": {
                    Intent intent = new Intent(context, Pln_produk_pasca.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "paket_sms_telepon": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "uang_elektronik": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "air_pdam": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "voucher_game": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "internet": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "tv": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "voucher": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "bpjs_kesehatan": {
                    Intent intent = new Intent(context, produkBPJS.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "angsuran_krefit": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "pajak_pbb": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "gas_negara": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "https://shopee": {
                    Intent intent = new Intent(context, produkWarungAbata.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    context.startActivity(intent);
                    break;
                }
                case "transfer": {
                    Intent intent = new Intent(context, transferBank.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    break;
                }
                case "Produk_pra": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "BRIVA": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "produk_digipos": {
                    Intent intent = new Intent(context, holder_pulsa_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    Preference.setPascatype(context,modelMenuUtama.getUrl());
                    Preference.setNoType(context, modelMenuUtama.getType());
                    intent.putExtra("name", modelMenuUtama.getName());
                    context.startActivity(intent);
                    break;
                }
                case "c_shop":{

                    String url = "https://c-shop.id/";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        // Chrome browser presumably not installed so allow user to choose instead
                    Toast.makeText(v.getContext(), ex.toString(),Toast.LENGTH_LONG).show();
                    }
                    break;
                }

            }

        });

    }

    @Override
    public int getItemCount() {
        return menuUtamas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconmenu;
        TextView titlemenu;
        LinearLayout linlistmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconmenu = itemView.findViewById(R.id.iconmenuutama);
            titlemenu = itemView.findViewById(R.id.titlemenuutama);
            linlistmenu = itemView.findViewById(R.id.Linlistmenu);
        }
    }

}
