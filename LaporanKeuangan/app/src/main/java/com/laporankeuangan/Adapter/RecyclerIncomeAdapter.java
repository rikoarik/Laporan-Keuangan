package com.laporankeuangan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.laporankeuangan.DetailtransaksiActivity;
import com.laporankeuangan.Model.ItemsIncomeModel;
import com.laporankeuangan.Model.ItemsOutcomeModel;
import com.laporankeuangan.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerIncomeAdapter extends RecyclerView.Adapter<RecyclerIncomeAdapter.MyViewHolder> {
    Context context;
    List<ItemsIncomeModel> itemsIncomeModel;


    public RecyclerIncomeAdapter(Context context, List<ItemsIncomeModel> itemsIncomeModel) {
        this.context = context;
        this.itemsIncomeModel = itemsIncomeModel;

     
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ItemsIncomeModel itemincome = itemsIncomeModel.get(position);
        holder.kategori.setText(itemincome.getKategori());
        holder.tanggal.setText(itemincome.getTanggal());
        holder.jumlah.setText(formatRupiah(Double.parseDouble(Integer.toString(itemincome.getJumlah()))));
        holder.waktu.setText(itemincome.getTime());

        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.bt.getContext(), DetailtransaksiActivity.class);

                intent.putExtra("kategori", itemincome.getKategori());
                intent.putExtra("jumlah", itemincome.getJumlah());
                intent.putExtra("tanggal", itemincome.getTanggal());
                intent.putExtra("time", itemincome.getTime());
                intent.putExtra("keterangan", itemincome.getKeterangan());
                intent.putExtra("imgurl", itemincome.getimgurl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.bt.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsIncomeModel.size();
    }
    

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView kategori;
        TextView jumlah;
        TextView tanggal;
        TextView waktu;
        ImageView bt, imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bt = itemView.findViewById(R.id.btdetail);
            kategori = itemView.findViewById(R.id.kategori_tran);
            jumlah = itemView.findViewById(R.id.saldotransaksi);
            tanggal = itemView.findViewById(R.id.datetransaksi);
            waktu = itemView.findViewById(R.id.timetransaksi);
            imageView = itemView.findViewById(R.id.imagedetail);


        };

    }
    
    public String formatRupiah(Double number){
        Locale localeId = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        return formatRupiah.format(number);
    }


}
