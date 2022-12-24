package com.laporankeuangan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

public class DetailtransaksiActivity extends AppCompatActivity {
    private TextView kategori, jumlah, tanggal, time, notes;
    private ImageView imageView;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailtransaksi);

        ImageButton back = findViewById(R.id.buttonback1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        kategori = findViewById(R.id.kategorydetail);
        jumlah = findViewById(R.id.detailsaldo);
        tanggal = findViewById(R.id.datedetail);
        time = findViewById(R.id.timedetail);
        notes = findViewById(R.id.notedetail);
        imageView = findViewById(R.id.imagedetail);

        kategori.setText(getIntent().getStringExtra("kategori"));
        jumlah.setText(formatRupiah(Double.parseDouble(Integer.toString(Integer.parseInt(String.valueOf(getIntent().getIntExtra("jumlah", 1)))))));
        tanggal.setText(getIntent().getStringExtra("tanggal"));
        time.setText(getIntent().getStringExtra("time")+" WIB");
        notes.setText(getIntent().getStringExtra("keterangan"));
        Glide.with(this).load(getIntent().getStringExtra("imgurl")).into(imageView);


    }
    public String formatRupiah(Double number){
        Locale localeId = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        return formatRupiah.format(number);
    }

}