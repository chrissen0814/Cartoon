package com.chrissen.cartoon.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.list.ListAdapter;
import com.chrissen.cartoon.adapter.pager.ContentPagerAdapter;
import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.bean.ContentBean;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.manager.BookDaoManager;
import com.chrissen.cartoon.dao.manager.BookNetDaoManager;
import com.chrissen.cartoon.fragment.ContentFragment;
import com.chrissen.cartoon.module.presenter.content.ContentPresenter;
import com.chrissen.cartoon.module.view.BookContentView;
import com.chrissen.cartoon.util.AnimUtil;
import com.chrissen.cartoon.util.IntentConstants;
import com.chrissen.cartoon.util.SystemUtil;
import com.chrissen.cartoon.util.view.dialog.BrightnessDialog;

import java.util.ArrayList;
import java.util.List;

public class ContentAbstractActivity extends BaseAbstractActivity implements BookContentView {

    private ContentPresenter mPresenter;

    private ViewPager mViewPager;
    private TextView mTitleTv , mIndexTv;

    private FrameLayout mTopFL , mBottomFl;
    private FrameLayout mListContentFl , mBlankFl;
    private RecyclerView mListRv;

    private ContentPagerAdapter mAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();

    private int counts;

    private String mComicName;
    private String mChapterId;
    private Book mBook;
    private String mChapterName;
    private ArrayList<ChapterBean.Chapter> mChapterArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initViews();
        initParams();
    }

    protected void initParams() {
        mComicName = getIntent().getStringExtra(IntentConstants.BOOK_NAME);
        mChapterId = getIntent().getStringExtra(IntentConstants.CHAPTER_ID);
        mBook = (Book) getIntent().getSerializableExtra(IntentConstants.BOOK);
        mChapterName = getIntent().getStringExtra(IntentConstants.CHAPTER_NAME);
        mChapterArrayList = (ArrayList<ChapterBean.Chapter>) getIntent().getSerializableExtra(IntentConstants.CHAPTER_LIST);
        mPresenter = new ContentPresenter(this);
        mPresenter.getContent(mComicName,Integer.parseInt(mChapterId));
        mTitleTv.setText(mComicName);
    }

    protected void initViews() {
        mTopFL = findViewById(R.id.content_top_bar_fl);
        mBottomFl = findViewById(R.id.content_bottom_bar_fl);
        mViewPager = findViewById(R.id.content_vp);
        mTitleTv = findViewById(R.id.content_title_tv);
        mIndexTv = findViewById(R.id.content_index_tv);
        mListRv = findViewById(R.id.content_list_rv);
        mListContentFl = findViewById(R.id.list_layout_fl);
        mBlankFl = findViewById(R.id.content_list_blank_fl);
    }

    @Override
    public void onShowSuccess(ContentBean obj) {
        counts = obj.getImageList().size();
        long dbIndex = mBook.getImageIndex() + 1;
        String index = dbIndex + "/" + counts;
        mIndexTv.setText(index);
        for (int i = 0; i < obj.getImageList().size(); i++) {
            ContentFragment fragment = ContentFragment.newInstance(obj.getImageList().get(i));
            mFragmentList.add(fragment);
        }
        mAdapter = new ContentPagerAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        if (mChapterId.equals(mBook.getChapterId())) {
            mViewPager.setCurrentItem(Integer.valueOf((int) mBook.getImageIndex()),false);
        }
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
        if (AVUser.getCurrentUser() != null) {
            BookNetDaoManager.updateBook(new BookDaoManager().queryBookById(mBook.getId()));
        }
        SystemUtil.startAutoBrightness(this);
    }

    public void setTopAndBottomBarVisibility(){
        if (mTopFL.getVisibility() == View.VISIBLE || mBottomFl.getVisibility() == View.VISIBLE) {
            AnimUtil.slideOutFromUp(mTopFL,this);
            AnimUtil.slideOutFromBottom(mBottomFl,this);
        }else {
            AnimUtil.slideInFromUp(mTopFL,this);
            AnimUtil.slideInFromBottom(mBottomFl,this);
        }
    }


    public void onListClick(View view) {
//        ListDialog listDialog = ListDialog.newInstance(mBook,mComicName,mChapterId);
//        listDialog.show(getSupportFragmentManager(),null);
        ListAdapter listAdapter = new ListAdapter(this,mBook,mComicName,mChapterId,mChapterArrayList);
        mListRv.setLayoutManager(new LinearLayoutManager(this));
        mListRv.setAdapter(listAdapter);
        AnimUtil.slideInFromLeft(mListContentFl,this);
        AnimUtil.fadeIn(mBlankFl,this);
    }

    public void onTimerClick(View view) {
    }

    public void onBrightnessClick(View view) {
        BrightnessDialog brightnessDialog = BrightnessDialog.newInstance();
        brightnessDialog.show(getSupportFragmentManager(),null);
    }


    public void onBlankClick(View view) {
        AnimUtil.slideOutToLeft(mListContentFl,this);
        AnimUtil.fadeOut(mBlankFl,this);
    }

}
