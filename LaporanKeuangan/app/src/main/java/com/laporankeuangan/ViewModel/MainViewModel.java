package com.laporankeuangan.ViewModel;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.laporankeuangan.Model.MainModel;
import com.laporankeuangan.Model.ResponseModel;
import com.laporankeuangan.R;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainViewModel extends ViewModel {
    private final static  String TAG = MainViewModel.class.getSimpleName();
    private FirebaseFirestore db;
    private TextView mSaldo;
    private TextView mIncome;
    private TextView mOutcome;
    private TextView mDateIncome;
    private TextView mDateOutcome;


    public LiveData<ResponseModel> saveTransaksi(Activity activity, MainModel mainModel){
        MutableLiveData<ResponseModel> saveTransaksiLiveData = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();


        return saveTransaksiLiveData;
    }
    private void showData(){
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
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                });

    }
    public String formatRupiah(Double number){
        Locale localeId = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        return formatRupiah.format(number);
    }
}
