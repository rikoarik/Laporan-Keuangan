package com.laporankeuangan.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.laporankeuangan.Fragment.IncomeFragment;
import com.laporankeuangan.Fragment.OutcomeFragment;
import com.laporankeuangan.InputActivity;

import java.util.ArrayList;
import java.util.List;

public class mAdapter extends FragmentPagerAdapter {
    private InputActivity myContext;
    int totalTabs;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public mAdapter(InputActivity context, FragmentManager fragmentManager, int totalTabs) {
        super(fragmentManager);

        myContext = context;
        this.totalTabs = totalTabs;
    }
    public void addFragment(Fragment InputFragment, String title) {
        mFragmentList.add(InputFragment);
        mFragmentTitleList.add(title);
    }




    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                IncomeFragment incomeFragment = new IncomeFragment();

                return incomeFragment;
            case 1:
                OutcomeFragment outcomeFragment = new OutcomeFragment();
                return outcomeFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return totalTabs;
    }
}

