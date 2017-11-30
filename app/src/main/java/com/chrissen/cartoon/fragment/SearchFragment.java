package com.chrissen.cartoon.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.BaseAbstractActivity;
import com.chrissen.cartoon.adapter.list.SearchBookAdapter;
import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.module.presenter.search.SearchPresenter;
import com.chrissen.cartoon.module.view.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by chris on 2017/11/20.
 */

public class SearchFragment extends Fragment implements SearchView {

    private static final String FRAGMENT_NAME = "SearchFragment";

    private EditText mEditText;
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    private boolean isLoadingMore;
    private int skipCounts;
    private List<BookBean.Book> mBookList = new ArrayList<>();
    private SearchBookAdapter mAdapter;

    private SearchPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        findViews(view);
        initParams();
        return view;
    }

    private void initParams() {
        mAdapter = new SearchBookAdapter(mBookList,getContext() , 2);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new SearchPresenter(this);
        mEditText.requestFocus();
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    mImageView.setImageResource(R.drawable.icon_search);
                }else {
                    mImageView.setImageResource(R.drawable.icon_search_default);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseAbstractActivity)getActivity()).putBindClick(v);
                hideSoftKeyboard();
                String bookName = mEditText.getText().toString();
                if (bookName.equals("")) {
                    Toasty.error(getContext(),getString(R.string.toast_search_et_null) , Toast.LENGTH_SHORT).show();
                }else {
                    isLoadingMore = false;
                    skipCounts = 0;
                    mPresenter.searchBook(bookName);
                }
                ((BaseAbstractActivity)getActivity()).resetClick();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1000);
                isLoadingMore = true;
                mPresenter.searchBook(mEditText.getText().toString());
            }
        });
    }

    private void findViews(View view) {
        mEditText = view.findViewById(R.id.search_input_text_et);
        mImageView = view.findViewById(R.id.search_search_iv);
        mRefreshLayout = view.findViewById(R.id.search_srl);
        mRecyclerView = view.findViewById(R.id.search_book_list_rv);
    }

    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    @Override
    public void onShowSuccess(BookBean obj) {
        if (isLoadingMore) {
            mBookList.addAll(obj.getBookList());
        }else {
            mBookList.clear();
            mBookList.addAll(obj.getBookList());
        }
        mAdapter.notifyDataSetChanged();
        skipCounts = Integer.parseInt(obj.getLimit());
    }

    @Override
    public void onShowError(String errorMsg) {
        Toasty.error(getContext(),errorMsg,Toast.LENGTH_SHORT).show();
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
