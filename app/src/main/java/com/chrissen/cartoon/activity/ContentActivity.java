package com.chrissen.cartoon.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.pager.ContentPagerAdapter;
import com.chrissen.cartoon.bean.ContentBean;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.manager.BookDaoManager;
import com.chrissen.cartoon.fragment.ContentFragment;
import com.chrissen.cartoon.module.presenter.content.ContentPresenter;
import com.chrissen.cartoon.module.view.BookContentView;
import com.chrissen.cartoon.util.IntentConstants;

import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends AppCompatActivity implements BookContentView {

    private ContentPresenter mPresenter;

    private ViewPager mViewPager;
    private TextView mTitleTv , mIndexTv;

    private ContentPagerAdapter mAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();

    private int counts;

    private String mComicName;
    private String mChapterId;
    private Book mBook;
    private String mChapterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initViews();
        initParams();
    }

    private void initParams() {
        mComicName = getIntent().getStringExtra(IntentConstants.BOOK_NAME);
        mChapterId = getIntent().getStringExtra(IntentConstants.CHAPTER_ID);
        mBook = (Book) getIntent().getSerializableExtra(IntentConstants.BOOK);
        mChapterName = getIntent().getStringExtra(IntentConstants.CHAPTER_NAME);
        mPresenter = new ContentPresenter(this);
        mPresenter.getContent(mComicName,Integer.parseInt(mChapterId));
        mTitleTv.setText(mComicName);
    }

    private void initViews() {
        mViewPager = findViewById(R.id.content_vp);
        mTitleTv = findViewById(R.id.content_title_tv);
        mIndexTv = findViewById(R.id.content_index_tv);
    }

    @Override
    public void onShowSuccess(ContentBean obj) {
        counts = obj.getImageList().size();
        int dbIndex = mBook.getImageIndex() + 1;
        String index = dbIndex + "/" + counts;
        mIndexTv.setText(index);
        for (int i = 0; i < obj.getImageList().size(); i++) {
            ContentFragment fragment = ContentFragment.newInstance(obj.getImageList().get(i));
            mFragmentList.add(fragment);
        }
        mAdapter = new ContentPagerAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(Integer.valueOf(mBook.getImageIndex()),false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int count = position + 1;
                String indexStr = count + "/" + counts;
                mIndexTv.setText(indexStr);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onShowError(String errorMsg) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBook.setUpdatedTime(System.currentTimeMillis());
        mBook.setChapterId(mChapterId);
        mBook.setChapterName(mChapterName);
        mBook.setImageIndex(mViewPager.getCurrentItem());
        new BookDaoManager().updateBook(mBook);
    }



}
