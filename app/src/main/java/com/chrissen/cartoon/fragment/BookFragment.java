package com.chrissen.cartoon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.list.BookAdapter;
import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.module.presenter.book.BookPresenter;
import com.chrissen.cartoon.module.view.BookListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 2017/11/18.
 */

public class BookFragment extends Fragment implements BookListView {

    private static final String FRAGMENT_NAME = "BookFragment";

    private static final int ALL = -1;
    private static final String BOOK_TYPE = "book_type";

    private String bookType;
    private boolean isLoadingMore;
    private int skipCounts;

    private BookPresenter mPresenter;

    private List<BookBean.Book> mBookList = new ArrayList<>();
    private SmartRefreshLayout mRefreshLayout;
    private BookAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static BookFragment newInstance(String bookType){
        Bundle bundle = new Bundle();
        bundle.putString(BOOK_TYPE,bookType);
        BookFragment fragment = new BookFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookType = getArguments().getString(BOOK_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mRefreshLayout = view.findViewById(R.id.book_refresh_layout);
        mRecyclerView = view.findViewById(R.id.book_rv);
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
        mAdapter = new BookAdapter(getContext(),mBookList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
