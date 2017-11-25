package com.chrissen.cartoon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.chrissen.cartoon.CartoonApplication;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.system.FeedbackActivity;
import com.chrissen.cartoon.adapter.list.DbBookAdapter;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.manager.BookDaoManager;
import com.chrissen.cartoon.util.AnimUtil;

import java.util.List;

/**
 * Created by chris on 2017/11/20.
 */

public class MainFragment extends Fragment {

    private static final String FRAGMENT_NAME = "MainFragment";

    private CardView mNotificationCv;
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
        CartoonApplication.getFeedbackAgent().countUnreadFeedback(new FeedbackAgent.UnreadCountCallback() {
            @Override
            public void onUnreadCount(int i, AVException e) {
                if (i > 0) {
                    AnimUtil.slideInFromUp(mNotificationCv,getActivity());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AnimUtil.fadeOut(mNotificationCv,getActivity());
                            mNotificationCv.setVisibility(View.GONE);
                        }
                    },8000);
                }
            }
        });
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.main_rv);
        mNotificationCv = view.findViewById(R.id.main_notification_cv);
        mNotificationCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), FeedbackActivity.class));
                mNotificationCv.setVisibility(View.GONE);
            }
        });
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
