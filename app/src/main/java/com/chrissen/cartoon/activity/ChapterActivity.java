package com.chrissen.cartoon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.recyclerview.ChapterAdapter;
import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.module.presenter.chapter.ChapterPresenter;
import com.chrissen.cartoon.module.view.BookChapterView;
import com.chrissen.cartoon.util.IntentConstants;

import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends AppCompatActivity implements BookChapterView {

    private ChapterPresenter mPresenter;

    private RecyclerView mRecyclerView;
    private ChapterAdapter mAdapter;
    private List<ChapterBean.Chapter> mChapterList = new ArrayList<>();
    private String comicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        comicName = getIntent().getStringExtra(IntentConstants.BOOK_NAME);
        mRecyclerView = findViewById(R.id.chapter_rv);
        mAdapter = new ChapterAdapter(this,comicName,mChapterList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new ChapterPresenter(this);
        mPresenter.getChapterList(comicName,0);
    }

    @Override
    public void onShowSuccess(ChapterBean obj) {
        mChapterList.addAll(obj.getChapterList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowError(String errorMsg) {

    }
}
