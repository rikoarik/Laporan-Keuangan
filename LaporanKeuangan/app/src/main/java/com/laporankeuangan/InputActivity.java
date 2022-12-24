package com.laporankeuangan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.laporankeuangan.Adapter.mAdapter;
import com.laporankeuangan.Fragment.IncomeFragment;
import com.laporankeuangan.Fragment.OutcomeFragment;

public class InputActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Income"));
        tabLayout.addTab(tabLayout.newTab().setText("Outcome"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final mAdapter adapter = new mAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        adapter.addFragment(new IncomeFragment(), "Income");
        adapter.addFragment(new OutcomeFragment(), "Outcome");
        viewPager.setAdapter(adapter);

        ImageButton back = (ImageButton)findViewById(R.id.buttonback1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InputActivity.this,MainActivity.class);
                startActivity(i);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(InputActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

}