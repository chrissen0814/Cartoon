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

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.pager.TypePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 2017/11/20.
 */

public class TypeFragment extends Fragment {

    private String[] mTypes;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TypePagerAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type,container,false);
        initView(view);
        initParams();
        return view;
    }

    private void initView(View view) {
        mTabLayout = view.findViewById(R.id.type_tab_layout);
        mViewPager = view.findViewById(R.id.type_vp);
    }

    private void initParams() {
        mTypes = getResources().getStringArray(R.array.comic_type);
        for (int i = 0; i < mTypes.length; i++) {
            BookFragment fragment = BookFragment.newInstance(mTypes[i]);
            mFragmentList.add(fragment);
        }
        mAdapter = new TypePagerAdapter(getChildFragmentManager(),mFragmentList,mTypes);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTypes.length);
    }

}
