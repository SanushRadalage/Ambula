package com.intpro.decepticons;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class UserTypeViewPagerAdapter extends FragmentStatePagerAdapter {

    public UserTypeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        UserTypeSelectionFragment userTypeSelectionFragment = new UserTypeSelectionFragment();
        Bundle bundle = new Bundle();

        if (i == 0) {
            bundle.putInt("page", 0);
        }
        else if(i == 1){
            bundle.putInt("page", 1);
        }

        userTypeSelectionFragment.setArguments(bundle);

        return userTypeSelectionFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }


}
