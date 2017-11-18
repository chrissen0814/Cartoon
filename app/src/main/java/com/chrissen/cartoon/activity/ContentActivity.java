package com.chrissen.cartoon.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.viewpager.ContentPagerAdapter;
import com.chrissen.cartoon.bean.ContentBean;
import com.chrissen.cartoon.fragment.ContentFragment;
import com.chrissen.cartoon.module.presenter.content.ContentPresenter;
import com.chrissen.cartoon.module.view.BookContentView;
import com.chrissen.cartoon.util.IntentConstants;

import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends AppCompatActivity implements BookContentView {

    private ContentPresenter mPresenter;

    private ViewPager mViewPager;
    private ContentPagerAdapter mAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();

    private String mComicName;
    private String mChapterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mComicName = getIntent().getStringExtra(IntentConstants.BOOK_NAME);
        mChapterId = getIntent().getStringExtra(IntentConstants.CHAPTER_ID);
        mViewPager = findViewById(R.id.content_vp);
        mPresenter = new ContentPresenter(this);
        mPresenter.getContent(mComicName,Integer.parseInt(mChapterId));
    }

    @Override
    public void onShowSuccess(ContentBean obj) {
        for (int i = 0; i < obj.getImageList().size(); i++) {
            ContentFragment fragment = ContentFragment.newInstance(obj.getImageList().get(i));
            mFragmentList.add(fragment);
        }
        mAdapter = new ContentPagerAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onShowError(String errorMsg) {

    }
}
