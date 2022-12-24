package com.laporankeuangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.laporankeuangan.Adapter.RecyclerIncomeAdapter;
import com.laporankeuangan.Adapter.TransaksiAdapter;
import com.laporankeuangan.Adapter.mAdapter;
import com.laporankeuangan.Fragment.IncomeFragment;
import com.laporankeuangan.Fragment.OutcomeFragment;
import com.laporankeuangan.Model.ItemsIncomeModel;
import com.laporankeuangan.Model.ItemsOutcomeModel;

import java.util.ArrayList;
import java.util.List;


public class TransaksiActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;

 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        tabLayout=(TabLayout)findViewById(R.id.tabLayoutTransaksi);
        viewPager=(ViewPager)findViewById(R.id.viewPagerTransaksi);
        tabLayout.addTab(tabLayout.newTab().setText("Income"));
        tabLayout.addTab(tabLayout.newTab().setText("Outcome"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final TransaksiAdapter adapter = new TransaksiAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        adapter.addFragment(new IncomeFragment(), "Income");
        adapter.addFragment(new OutcomeFragment(), "Outcome");
        viewPager.setAdapter(adapter);

        ImageButton back = findViewById(R.id.buttonback1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });







    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}