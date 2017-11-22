package com.chrissen.cartoon.util.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.list.ListAdapter;
import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.module.presenter.chapter.ChapterPresenter;
import com.chrissen.cartoon.module.view.BookChapterView;

/**
 * Created by chris on 2017/11/22.
 */

public class ListDialog extends DialogFragment implements BookChapterView {

    private static final String BOOK = "book";
    private static final String COMIC_NAME = "comic_name";
    private static final String CHAPTER_ID = "chapter_id";

    private ChapterPresenter mPresenter;
    private Book mBook;
    private String mComicName , mChapterId;

    private RecyclerView mRecyclerView;

    public static ListDialog newInstance(Book book ,String comicName , String chapterId){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BOOK,book);
        bundle.putString(COMIC_NAME,comicName);
        bundle.putString(CHAPTER_ID,chapterId);
        ListDialog dialog = new ListDialog();
        dialog.setArguments(bundle);
        return dialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ChapterPresenter(this);
        mBook = (Book) getArguments().getSerializable(BOOK);
        mComicName = getArguments().getString(COMIC_NAME);
        mChapterId = getArguments().getString(CHAPTER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_list,container,false);
        initViews(view);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        getDialog().getWindow().setWindowAnimations(R.style.ListDialog_Animation);
        mPresenter.getChapterList(mComicName,0);
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.list_rv);
    }


    @Override
    public void onShowSuccess(ChapterBean obj) {
        ListAdapter adapter = new ListAdapter(getContext(),mBook,mComicName,mChapterId ,obj.getChapterList());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onShowError(String errorMsg) {

    }
}
