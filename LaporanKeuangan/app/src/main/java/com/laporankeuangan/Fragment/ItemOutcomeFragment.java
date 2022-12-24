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
import com.laporankeuangan.Adapter.RecyclerOutcomeAdapter;
import com.laporankeuangan.Model.ItemsIncomeModel;
import com.laporankeuangan.Model.ItemsOutcomeModel;
import com.laporankeuangan.R;

import java.util.ArrayList;
import java.util.List;


public class ItemOutcomeFragment extends Fragment {
    List<ItemsOutcomeModel> listOutcomeItem;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    RecyclerOutcomeAdapter Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_outcome, container, false);
        recyclerView = view.findViewById(R.id.outcomelist);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        listOutcomeItem = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        getDataOutcome();
        return view;
    }

    public void getDataOutcome(){
        db.collection("main")
                .document("main")
                .collection("outcome")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot dc : task.getResult()){
                                ItemsOutcomeModel itemsOutcomeModel = new ItemsOutcomeModel();
                                itemsOutcomeModel.setKategori(dc.getString("kategori"));
                                itemsOutcomeModel.setTanggal(dc.getString("tanggal"));
                                itemsOutcomeModel.setTime(dc.getString("time"));
                                itemsOutcomeModel.setJumlah(dc.getLong("jumlah").intValue());
                                itemsOutcomeModel.setKeterangan(dc.getString("keterangan"));
                                itemsOutcomeModel.setimgurl(dc.getString("imgurl"));
                                listOutcomeItem.add(itemsOutcomeModel);

                            }
                        }
                        Adapter = new RecyclerOutcomeAdapter(getContext(), listOutcomeItem);
                        recyclerView.setAdapter(Adapter);

                    }
                });

    }
}