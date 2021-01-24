package com.dreamernguyen.demoyokafo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BottomNavAdapter extends FragmentStatePagerAdapter {


    public BottomNavAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new HomeFragment();
            case 1 : return new MessageFragment();
            case 2 : return new NotificationFragment();
            case 3 : return new ProfileFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
