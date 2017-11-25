package com.chrissen.cartoon.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.list.ChapterAdapter;
import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.module.presenter.chapter.ChapterPresenter;
import com.chrissen.cartoon.module.view.BookChapterView;
import com.chrissen.cartoon.util.IntentConstants;

import java.util.ArrayList;

public class ChapterActivity extends BaseAbstractActivity implements BookChapterView {

    private ChapterPresenter mPresenter;

    private RecyclerView mRecyclerView;
    private ChapterAdapter mAdapter;
    private ArrayList<ChapterBean.Chapter> mChapterList = new ArrayList<>();
    private String comicName;
    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        initViews();
        initParams();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mPresenter = new ChapterPresenter(this);
        mPresenter.getChapterList(comicName,0);
    }

    @Override
    public void onShowSuccess(ChapterBean obj) {
        mChapterList.clear();
        mChapterList.addAll(obj.getChapterList());
        mAdapter.notifyDataSetChanged();
        String chapterId = mBook.getChapterId();
        int index = 0;
        if (chapterId != null) {
            for (int i = 0; i < obj.getChapterList().size(); i++) {
                if (chapterId.equals(obj.getChapterList().get(i).getId())) {
                    break;
                }else {
                    index++;
                }
            }
        }
        mRecyclerView.smoothScrollToPosition(index);
        mAdapter.setPosStatus(index);
    }

    @Override
    public void onShowError(String errorMsg) {

    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.chapter_rv);
    }

    @Override
    protected void initParams() {
        comicName = getIntent().getStringExtra(IntentConstants.BOOK_NAME);
        mBook = (Book) getIntent().getSerializableExtra(IntentConstants.BOOK);
        mAdapter = new ChapterAdapter(this,mBook,comicName,mChapterList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
