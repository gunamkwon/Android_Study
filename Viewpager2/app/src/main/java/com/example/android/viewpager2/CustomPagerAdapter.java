package com.example.android.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();

    public CustomPagerAdapter(FragmentActivity fa)
    {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment)
    {
        fragmentList.add(fragment);
        notifyItemInserted(fragmentList.size()-1);
    }

    public void removeFragment(){
        fragmentList.remove(fragmentList.size());
        notifyItemRemoved(fragmentList.size());
    }
}
