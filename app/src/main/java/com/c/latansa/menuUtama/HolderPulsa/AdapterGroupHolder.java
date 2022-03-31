package com.c.latansa.menuUtama.HolderPulsa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.latansa.Api.Api;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.R;
import com.c.latansa.sharePreference.Preference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterGroupHolder extends RecyclerView.Adapter<AdapterGroupHolder.ViewHolder> {
    Context context;
    ArrayList<ResponGroup.Data> datasub;


    public AdapterGroupHolder(Context context, ArrayList<ResponGroup.Data> datasub) {
        this.context = context;
        this.datasub = datasub;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_groupkategori, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponGroup.Data data = datasub.get(position);
        holder.namesub.setText(data.getName());


        holder.linierSubCategory.setOnClickListener(v -> {

            Intent intent = new Intent(context, produkholder.class);
            intent.putExtra("id", data.getId());
            intent.putExtra("jenis","sub");
            intent.putExtra("name", data.getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return datasub.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linierSubCategory;
        TextView namesub;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linierSubCategory = itemView.findViewById(R.id.linierSubCategory);
            namesub = itemView.findViewById(R.id.namesub);


        }
    }

}
