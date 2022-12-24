package com.laporankeuangan.ViewModel;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;
import com.laporankeuangan.Model.IncomeModel;
import com.laporankeuangan.Model.MainModel;
import com.laporankeuangan.Model.ResponseModel;
import com.laporankeuangan.Model.UploadImagesModel;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class IncomeViewModel extends ViewModel {
    private final static String TAG = IncomeViewModel.class.getSimpleName();

    private FirebaseFirestore db;


    public LiveData<ResponseModel> saveIncome(Activity activity, IncomeModel incomeModel){
        MutableLiveData<ResponseModel> saveIncomeLiveData = new MutableLiveData<>();

        db = FirebaseFirestore.getInstance();
        @SuppressLint
                ("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String Time = sdf.format(new Date());


        if (incomeModel.equals(null)){
            saveIncomeLiveData.postValue(new ResponseModel(false, "Transaksi is Empty"));
        } else {
            if (incomeModel.getJumlah()== 0){
                saveIncomeLiveData.postValue(new ResponseModel(false, "Kategori is Empty"));
            } else {
                Map<String, Object> transaksi = new HashMap<>();
                transaksi.put("timestamp", FieldValue.serverTimestamp());
                transaksi.put("time", Time);
                transaksi.put("imgurl", incomeModel.getimgurl());
                transaksi.put("jumlah", incomeModel.getJumlah());
                transaksi.put("kategori", incomeModel.getKategori());
                transaksi.put("keterangan", incomeModel.getNotes());
                transaksi.put("tanggal", incomeModel.getTanggal());

                db.collection("main").document("main")
                        .collection("income").add(transaksi)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                saveIncomeLiveData.postValue(new ResponseModel(true, "Success"));

                                //////
                                db.collection("main")
                                        .document("main").get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                DocumentSnapshot document = task.getResult();

                                                MainModel mainModel = new MainModel();
                                                mainModel.setSaldosekarang(document.getLong("saldo").intValue());

                                                Log.e("TRANSAKSI SALDO", "SALDO AWAL : " + mainModel.getSaldosekarang());
                                                updateSaldo( incomeModel.getJumlah(), mainModel.getSaldosekarang());
                                                Log.e("UPDATE SALDO", String.valueOf(incomeModel.getKategori()) + " " + String.valueOf(incomeModel.getJumlah()) + " " + String.valueOf(mainModel.getSaldosekarang()));
                                            }
                                        })
                                        .addOnFailureListener(e -> {

                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                saveIncomeLiveData.postValue(new ResponseModel(true, e.getMessage()));
                            }
                        });
            }
        }
        return saveIncomeLiveData;
    }

    public void updateSaldo(int total, int saldoSekarang){
        int saldo;

        db = FirebaseFirestore.getInstance();
        saldo = saldoSekarang + total;
        db.collection("main").document("main")
                .update("saldo", saldo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("SALDO BERHASIL", String.valueOf(saldo));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                });
    }
}
