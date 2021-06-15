package com.calista.atma_bbq.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.calista.atma_bbq.R;
import com.calista.atma_bbq.api.ApiClient;
import com.calista.atma_bbq.api.ApiService;
import com.calista.atma_bbq.model.Menu;
import com.calista.atma_bbq.ui.menu.MenuDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> implements Filterable {
    private Context context;
    private List<Menu> menuList;
    private List<Menu> filteredMenu;

    public MenuAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
        this.filteredMenu = menuList;
        notifyDataSetChanged();
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                FilterResults results = new FilterResults();

                if (charSequenceString.isEmpty()){
                    filteredMenu = menuList;
                }
                else{
                    List<Menu> filteredList = new ArrayList<>();
                    for (Menu menu : menuList){
                        if(menu.getNama_menu().toLowerCase().contains(charSequenceString.toLowerCase())){
                            filteredList.add(menu);

                        }
                    }
                    filteredMenu = filteredList;
                }


                results.values = filteredMenu;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredMenu = (List<Menu>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.menu_item, parent,false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        final Menu menu = filteredMenu.get(position);
        holder.nama_tv.setText(menu.getNama_menu());
        holder.deskripsi_tv.setText(menu.getDeskripsi());
        holder.harga_menu_tv.setText("Rp. "+String.valueOf(menu.getHarga_menu()));

        //set image from url
        Glide.with(context)
                .load(ApiClient.BASE_URL + menu.getGambar_menu())
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(holder.gambar_menu_iv);

        holder.menu_item_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("menu",menu);

                Intent intent = new Intent(context, MenuDetailActivity.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredMenu.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{
        private CardView menu_item_cv;
        private TextView nama_tv,harga_menu_tv,deskripsi_tv;
        private ImageView gambar_menu_iv;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_item_cv = itemView.findViewById(R.id.menu_item_cv);
            nama_tv = itemView.findViewById(R.id.nama_tv);
            harga_menu_tv = itemView.findViewById(R.id.harga_menu_tv);
            deskripsi_tv = itemView.findViewById(R.id.deskripsi_tv);
            gambar_menu_iv = itemView.findViewById(R.id.gambar_menu_iv);
        }
    }

}
