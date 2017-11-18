package com.chrissen.cartoon.adapter.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by chris on 2017/11/18.
 */

public class TypePagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private List<Fragment> mFragmentList;

    public TypePagerAdapter(FragmentManager fm , List<Fragment> fragmentList , String[] titles) {
        super(fm);
        mFragmentList = fragmentList;
        mTitles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
