package com.chrissen.cartoon.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.viewpager.TypePagerAdapter;
import com.chrissen.cartoon.fragment.TypeFragment;

import java.util.ArrayList;
import java.util.List;

public class TypeActivity extends AppCompatActivity {

    private String[] mTypes;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TypePagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        initView();
        initParams();
    }

    private void initView() {
        mTabLayout = findViewById(R.id.type_tab_layout);
        mViewPager = findViewById(R.id.type_vp);
    }

    private void initParams() {
        mTypes = getResources().getStringArray(R.array.comic_type);
        for (int i = 0; i < mTypes.length; i++) {
            TypeFragment fragment = TypeFragment.newInstance(mTypes[i]);
            mFragmentList.add(fragment);
        }
        mAdapter = new TypePagerAdapter(getSupportFragmentManager(),mFragmentList,mTypes);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTypes.length);
    }


}
