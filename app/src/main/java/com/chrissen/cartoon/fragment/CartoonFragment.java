package com.chrissen.cartoon.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.list.DbBookAdapter;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.manager.BookDaoManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartoonFragment extends Fragment {

    private static final String FRAGMENT_NAME = "CartoonFragment";

    private RecyclerView mRecyclerView;
    private DbBookAdapter mAdapter;


    public CartoonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cartoon, container, false);
        findViews(view);
        initParams();
        return view;
    }

    private void initParams() {

    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.main_rv);
    }

    @Override
    public void onStart() {
        super.onStart();
        List<Book> bookList = new BookDaoManager().queryAllBook();
        if (bookList != null && bookList.size() > 0) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        mAdapter = new DbBookAdapter(getContext(),bookList);
        mRecyclerView.setAdapter(mAdapter);
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
