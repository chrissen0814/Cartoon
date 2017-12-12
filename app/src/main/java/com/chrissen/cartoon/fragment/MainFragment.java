package com.chrissen.cartoon.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.pager.MainPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 2017/11/20.
 */

public class MainFragment extends Fragment {

    private static final String FRAGMENT_NAME = "MainFragment";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MainPagerAdapter mPagerAdapter;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        findViews(view);
        initParams();
        return view;
    }

    private void initParams() {
        mFragmentList.add(new CartoonFragment());
        mFragmentList.add(new TxtFragment());
        mTitleList.add("漫画");
        mTitleList.add("文本");
        mPagerAdapter = new MainPagerAdapter(getChildFragmentManager(),mFragmentList,mTitleList);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void findViews(View view) {
        mTabLayout = view.findViewById(R.id.main_tab_layout);
        mViewPager = view.findViewById(R.id.main_view_pager);
    }


    @Override
    public void onResume() {
        super.onResume();
        AVAnalytics.onFragmentStart(FRAGMENT_NAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        AVAnalytics.onFragmentEnd(FRAGMENT_NAME);
    }
}
