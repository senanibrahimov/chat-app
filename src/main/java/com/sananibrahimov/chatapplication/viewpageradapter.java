package com.sananibrahimov.chatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class viewpageradapter extends FragmentPagerAdapter {

    String tab[]={"users","chat"};
    public viewpageradapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new usersfragment();
        }
        else {
            return new chatpart();
        }

    }

    @Override
    public int getCount() {
        return tab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tab[position];
    }
}
