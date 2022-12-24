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
import com.laporankeuangan.Model.OutcomeModel;
import com.laporankeuangan.Model.MainModel;
import com.laporankeuangan.Model.ResponseModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class OutcomeViewModel extends ViewModel {
    private final static String TAG = OutcomeViewModel.class.getSimpleName();

    private FirebaseFirestore db;
    

    public LiveData<ResponseModel> saveOutcome(Activity activity, OutcomeModel OutcomeModel){
        MutableLiveData<ResponseModel> saveOutcomeLiveData = new MutableLiveData<>();
     
        db = FirebaseFirestore.getInstance();
        @SuppressLint
                ("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String Time = sdf.format(new Date());


        if (OutcomeModel.equals(null)){
            saveOutcomeLiveData.postValue(new ResponseModel(false, "Transaksi is Empty"));
        } else {
            if (OutcomeModel.getJumlah()== 0){
                saveOutcomeLiveData.postValue(new ResponseModel(false, "Kategori is Empty"));
            } else {
                Map<String, Object> transaksi = new HashMap<>();
                transaksi.put("timestamp", FieldValue.serverTimestamp());
                transaksi.put("time", Time);
                transaksi.put("imgname", OutcomeModel.getimgurl());
                transaksi.put("jumlah", OutcomeModel.getJumlah());
                transaksi.put("kategori", OutcomeModel.getKategori());
                transaksi.put("keterangan", OutcomeModel.getNotes());
                transaksi.put("tanggal", OutcomeModel.getTanggal());



                db.collection("main").document("main")
                        .collection("outcome").add(transaksi)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                saveOutcomeLiveData.postValue(new ResponseModel(true, "Success"));

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
                                                updateSaldo( OutcomeModel.getJumlah(), mainModel.getSaldosekarang());
                                                Log.e("UPDATE SALDO", String.valueOf(OutcomeModel.getKategori()) + " " + String.valueOf(OutcomeModel.getJumlah()) + " " + String.valueOf(mainModel.getSaldosekarang()));
                                            }
                                        })
                                        .addOnFailureListener(e -> {

                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                saveOutcomeLiveData.postValue(new ResponseModel(true, e.getMessage()));
                            }
                        });
            }
        }
        return saveOutcomeLiveData;
    }

    public void updateSaldo(int total, int saldoSekarang){
        int saldo;

        db = FirebaseFirestore.getInstance();
        saldo = saldoSekarang - total;
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
