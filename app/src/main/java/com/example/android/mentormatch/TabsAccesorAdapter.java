package com.example.android.mentormatch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAccesorAdapter extends FragmentPagerAdapter {

    MyProfile myProfile;
    Chat chat;
    Match match;

    public TabsAccesorAdapter(FragmentManager fm) {
        super(fm);
        myProfile = new MyProfile();
        chat = new Chat();
        match = new Match();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return myProfile;
            case 1:
                return match;
            case 2:
                return chat;
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
