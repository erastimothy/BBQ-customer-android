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
import com.calista.atma_bbq.model.Detail_Pesanan;
import com.calista.atma_bbq.model.Menu;
import com.calista.atma_bbq.ui.menu.MenuDetailActivity;
import com.calista.atma_bbq.ui.pesanan.DetailPesananActivity;

import java.util.ArrayList;
import java.util.List;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.PesananViewHolder> implements Filterable {
    private Context context;
    private List<Detail_Pesanan> detail_pesanans;
    private List<Detail_Pesanan> filtereddetail_pesanans;

    public PesananAdapter(Context context, List<Detail_Pesanan> detail_pesanans) {
        this.context = context;
        this.detail_pesanans = detail_pesanans;
        this.filtereddetail_pesanans = detail_pesanans;
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
                    filtereddetail_pesanans = detail_pesanans;
                }
                else{
                    List<Detail_Pesanan> filteredList = new ArrayList<>();
                    for (Detail_Pesanan detail_pesanan : detail_pesanans){
                        if(detail_pesanan.getMenu().getNama_menu().toLowerCase().contains(charSequenceString.toLowerCase())){
                            filteredList.add(detail_pesanan);

                        }
                    }
                    filtereddetail_pesanans = filteredList;
                }


                results.values = filtereddetail_pesanans;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filtereddetail_pesanans = (List<Detail_Pesanan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public PesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pesanan_item, parent,false);
        return new PesananAdapter.PesananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananViewHolder holder, int position) {
        final Detail_Pesanan detail_pesanan = filtereddetail_pesanans.get(position);
        holder.nama_tv.setText(detail_pesanan.getMenu().getNama_menu());
        holder.kuantitas_tv.setText(detail_pesanan.getKuantitas() + " " + detail_pesanan.getMenu().getUnit());
        holder.status_pesanan_tv.setText(detail_pesanan.getStatus_pesanan());

        //set image from url
        Glide.with(context)
                .load(ApiClient.BASE_URL + detail_pesanan.getMenu().getGambar_menu())
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(holder.gambar_menu_iv);

        holder.pesanan_item_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("detail_pesanan",detail_pesanan);

                Intent intent = new Intent(context, DetailPesananActivity.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return filtereddetail_pesanans.size();
    }

    public class PesananViewHolder extends RecyclerView.ViewHolder{
        private ImageView gambar_menu_iv;
        private TextView nama_tv,kuantitas_tv,status_pesanan_tv;
        private CardView pesanan_item_cv;

        public PesananViewHolder(@NonNull View itemView) {
            super(itemView);
            pesanan_item_cv = itemView.findViewById(R.id.pesanan_item_cv);
            nama_tv = itemView.findViewById(R.id.nama_tv);
            kuantitas_tv = itemView.findViewById(R.id.kuantitas_tv);
            status_pesanan_tv = itemView.findViewById(R.id.status_pesanan_tv);
            gambar_menu_iv = itemView.findViewById(R.id.gambar_menu_iv);
        }
    }
}
