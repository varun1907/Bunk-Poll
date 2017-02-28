package com.example.hp.bunkpoll;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by HP on 2/21/2017.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;



    public SimpleFragmentPagerAdapter(Context context,FragmentManager fm) {


        super(fm);
        mContext=context;

    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return new HistoryFragment();
        }
        else
        {
            return new PlanBunkButtonFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_history);
        }   else {
            return mContext.getString(R.string.category_plan_bunk);
        }
    }
}
