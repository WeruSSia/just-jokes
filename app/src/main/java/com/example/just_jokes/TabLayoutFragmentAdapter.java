package com.example.just_jokes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class TabLayoutFragmentAdapter extends FragmentStatePagerAdapter {

    private final String[] tabTitles;

    TabLayoutFragmentAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabTitles = new String[]{context.getString(R.string.home_tab), context.getString(R.string.favourites_tab)};
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new FavouritesFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
