package com.example.android.mentormatch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAccesorAdapter extends FragmentPagerAdapter {

    public TabsAccesorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return new MyProfile();
            case 1:
                return new Match();
            case 2:
                return new MyMatchesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "My Profile";
            case 1:
                return "Match";
            case 2:
                return "Chat";
            default:
                return null;
        }
    }
}
