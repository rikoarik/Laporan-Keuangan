package com.laporankeuangan.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.laporankeuangan.Adapter.RecyclerIncomeAdapter;
import com.laporankeuangan.Model.ItemsIncomeModel;
import com.laporankeuangan.R;

import java.util.ArrayList;
import java.util.List;


public class ItemIncomeFragment extends Fragment {
    List<ItemsIncomeModel> listIncomeItem;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    RecyclerIncomeAdapter Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_income, container, false);
        recyclerView = view.findViewById(R.id.incomelist);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        listIncomeItem = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        getDataIncome();
        return view;
    }

    public void getDataIncome() {
        db.collection("main")
                .document("main")
                .collection("income")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot dc : task.getResult()) {

                                ItemsIncomeModel itemsIncomeModel = new ItemsIncomeModel();
                                itemsIncomeModel.setKategori(dc.getString("kategori"));
                                itemsIncomeModel.setTanggal(dc.getString("tanggal"));
                                itemsIncomeModel.setTime(dc.getString("time"));
                                itemsIncomeModel.setJumlah(dc.getLong("jumlah").intValue());
                                itemsIncomeModel.setKeterangan(dc.getString("keterangan"));
                                itemsIncomeModel.setimgurl(dc.getString("imgurl"));
                                listIncomeItem.add(itemsIncomeModel);
                            }

                        }
                        Adapter = new RecyclerIncomeAdapter(getContext(), listIncomeItem);
                        recyclerView.setAdapter(Adapter);
                    }
                });


    }
}