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

import com.laporankeuangan.DetailtransaksiActivity;
import com.laporankeuangan.Model.ItemsIncomeModel;
import com.laporankeuangan.Model.ItemsOutcomeModel;
import com.laporankeuangan.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerOutcomeAdapter extends RecyclerView.Adapter<RecyclerOutcomeAdapter.MyViewHolder> {
    Context context;
    List<ItemsOutcomeModel> itemsOutcomeModels;

    public RecyclerOutcomeAdapter(Context context, List<ItemsOutcomeModel> itemsOutcomeModels) {
        this.context = context;
        this.itemsOutcomeModels = itemsOutcomeModels;
     
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ItemsOutcomeModel itemoutcome = itemsOutcomeModels.get(position);
        holder.kategori.setText(itemoutcome.getKategori());
        holder.tanggal.setText(itemoutcome.getTanggal());
        holder.jumlah.setText(formatRupiah(Double.parseDouble(Integer.toString(itemoutcome.getJumlah()))));
        holder.waktu.setText(itemoutcome.getTime());
        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.bt.getContext(), DetailtransaksiActivity.class);

                intent.putExtra("kategori", itemoutcome.getKategori());
                intent.putExtra("jumlah", itemoutcome.getJumlah());
                intent.putExtra("tanggal", itemoutcome.getTanggal());
                intent.putExtra("time", itemoutcome.getTime());
                intent.putExtra("keterangan", itemoutcome.getKeterangan());
                intent.putExtra("imgurl", itemoutcome.getimgurl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.bt.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsOutcomeModels.size();
    }
    

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView kategori;
        TextView jumlah;
        TextView tanggal;
        TextView waktu;
        ImageView bt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bt = itemView.findViewById(R.id.btdetail);
            kategori = itemView.findViewById(R.id.kategori_tran);
            jumlah = itemView.findViewById(R.id.saldotransaksi);
            tanggal = itemView.findViewById(R.id.datetransaksi);
            waktu = itemView.findViewById(R.id.timetransaksi);


        };

    }
    
    public String formatRupiah(Double number){
        Locale localeId = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        return formatRupiah.format(number);
    }


}
