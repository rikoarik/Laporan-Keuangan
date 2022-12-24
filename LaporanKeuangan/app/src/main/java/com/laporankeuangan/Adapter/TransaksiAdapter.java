package com.laporankeuangan.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.laporankeuangan.Fragment.ItemIncomeFragment;
import com.laporankeuangan.Fragment.ItemOutcomeFragment;
import com.laporankeuangan.TransaksiActivity;

import java.util.ArrayList;
import java.util.List;

public class TransaksiAdapter extends FragmentPagerAdapter {
    private TransaksiActivity myContext;
    int totalTabs;
    private final List<Fragment> FragmentList = new ArrayList<>();
    private final List<String> FragmentTitleList = new ArrayList<>();

    public TransaksiAdapter(TransaksiActivity context, FragmentManager fragmentManager, int totalTabs) {
        super(fragmentManager);

        myContext = context;
        this.totalTabs = totalTabs;
    }
    public void addFragment(Fragment ItemIncomeFragment, String title) {
        FragmentList.add(ItemIncomeFragment);
        FragmentTitleList.add(title);
    }




    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ItemIncomeFragment itemIncomeFragment = new ItemIncomeFragment();

                return itemIncomeFragment;
            case 1:
                ItemOutcomeFragment itemOutcomeFragment = new ItemOutcomeFragment();
                return itemOutcomeFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return totalTabs;
    }
}

