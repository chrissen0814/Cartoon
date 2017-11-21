package com.chrissen.cartoon.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.list.DbBookAdapter;
import com.chrissen.cartoon.bean.HitokotoBean;
import com.chrissen.cartoon.module.model.HitokotoModel;
import com.chrissen.cartoon.util.ConfigUtil;

/**
 * Created by chris on 2017/11/20.
 */

public class MainFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DbBookAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
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
        mAdapter = new DbBookAdapter(getContext());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mRecyclerView.setAdapter(mAdapter);
    }
}
