package com.chrissen.cartoon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.BookAdapter;
import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.module.presenter.book.BookPresenter;
import com.chrissen.cartoon.module.view.BookListView;
import com.chrissen.cartoon.util.IntentConstants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements BookListView {
    private static final int ALL = -1;

    private Toolbar mToolbar;
    private String bookType;
    private boolean isLoadingMore;
    private int skipCounts;

    private BookPresenter mPresenter;

    private List<BookBean.Book> mBookList = new ArrayList<>();
    private SmartRefreshLayout mRefreshLayout;
    private BookAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        mToolbar = findViewById(R.id.book_type);
        mRefreshLayout = findViewById(R.id.book_refresh_layout);
        mRecyclerView = findViewById(R.id.book_rv);
        bookType = getIntent().getStringExtra(IntentConstants.BOOK_TYPE);
        mToolbar.setTitle(bookType);
        mPresenter = new BookPresenter(this);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                isLoadingMore = false;
                skipCounts = 0;
                mPresenter.getBookList(bookType,skipCounts,ALL);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1000);
                isLoadingMore = true;
                mPresenter.getBookList(bookType,skipCounts,ALL);
            }
        });
        mAdapter = new BookAdapter(this,mBookList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getBookList(bookType,skipCounts,ALL);
    }


    @Override
    public void onShowSuccess(BookBean obj) {
        if (!isLoadingMore) {
            mBookList.clear();
            mBookList.addAll(obj.getBookList());
        }else {
            mBookList.addAll(obj.getBookList());
        }
        mAdapter.notifyDataSetChanged();
        skipCounts = Integer.parseInt(obj.getLimit());
    }

    @Override
    public void onShowError(String errorMsg) {

    }
}
