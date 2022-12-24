package com.laporankeuangan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.laporankeuangan.Model.MainModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    ImageButton tombol;
    private FirebaseFirestore db;
    private TextView mSaldo;
    private TextView mIncome, mDateincome, mTimeincome, mOutcome, mDateoutcome, mTimeoutcome;
    private int saldoku = 700000;

    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String date = sdf.format(new Date());
        TextView setDate = (TextView)findViewById(R.id.date);
        setDate.setText(date);

        mSaldo = (TextView)findViewById(R.id.saldo);
        mIncome = (TextView)findViewById(R.id.Income);
        mDateincome = findViewById(R.id.dateincome);
        mTimeincome = findViewById(R.id.timeincome);
        mOutcome = (TextView)findViewById(R.id.Outcome);
        mDateoutcome = findViewById(R.id.dateoutcome);
        mTimeoutcome = findViewById(R.id.timeoutcome);



        db = FirebaseFirestore.getInstance();
        getDataIncome();
        getDataOutcome();
        tombol = (ImageButton) findViewById(R.id.btadd);
        tombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, InputActivity.class);
                startActivity(i);
                finish();
            }
        });
        db.collection("main")
                .document("main")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot value = task.getResult();
                        int saldo = value.getLong("saldo").intValue();
                        MainModel mainModel = new MainModel();
                        mainModel.setSaldosekarang(saldo);

                        mSaldo.setText(formatRupiah(Double.parseDouble(Integer.toString(mainModel.getSaldosekarang()))));
                    }
                });


    }
    private void getDataIncome(){
        db.collection("main").document("main")
                .collection("income")
                .orderBy("timestamp", Query.Direction.DESCENDING).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int saldoincome = document.getLong("jumlah").intValue();
                                String date = document.getString("tanggal").toString();
                                String time = document.getString("time").toString();
                                MainModel mainModel = new MainModel();
                                mainModel.setIncomenow(saldoincome);
                                mainModel.setDateincome(date);
                                mainModel.setTimeincome(time);
                                mDateincome.setText(mainModel.getDateincome());
                                mTimeincome.setText(mainModel.getTimeincome());
                                mIncome.setText(formatRupiah(Double.parseDouble(Integer.toString(mainModel.getIncomenow()))));
                                Log.d(TAG, "jumlah " + saldoincome);
                            }


                        }
                    }
                });

    }
    private void getDataOutcome(){
        db.collection("main").document("main")
                .collection("outcome")
                .orderBy("timestamp", Query.Direction.DESCENDING).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int saldooutcome = document.getLong("jumlah").intValue();
                                String date = document.getString("tanggal").toString();
                                String time = document.getString("time").toString();
                                MainModel mainModel = new MainModel();
                                mainModel.setOutcomenow(saldooutcome);
                                mainModel.setDateOutcome(date);
                                mainModel.setTimeoutcome(time);
                                mDateoutcome.setText(mainModel.getDateOutcome());
                                mTimeoutcome.setText(mainModel.getTimeoutcome());
                                mOutcome.setText(formatRupiah(Double.parseDouble(Integer.toString(mainModel.getOutcomenow()))));
                                Log.d(TAG, "jumlah " + saldooutcome);
                            }


                        }
                    }
                });

    }

    @SuppressLint("NonConstantResourceId")
    public void click(View v) {
        Intent intent;
        if (v.getId() == R.id.bttransaksi) {
            intent = new Intent(this, TransaksiActivity.class);
        } else {
            throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        startActivity(intent);

    }
    public void chart(){

    }


    public String formatRupiah(Double number){
        Locale localeId = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        return formatRupiah.format(number);
    }

}