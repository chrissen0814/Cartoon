package com.chrissen.cartoon.adapter.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.ContentAbstractActivity;
import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.util.IntentConstants;

import java.util.ArrayList;

/**
 * Created by chris on 2017/11/18.
 */

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private Context mContext;
    private String mComicName;
    private ArrayList<ChapterBean.Chapter> mChapterList;
    private Book mBook;

    private int pos = -1;

    public ChapterAdapter(Context context, Book book , String comicName, ArrayList<ChapterBean.Chapter> chapterList) {
        mContext = context;
        mBook = book;
        mComicName = comicName;
        mChapterList = chapterList;
    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chapter,parent,false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {
        if (pos == position) {
            holder.bookmarkIv.setVisibility(View.VISIBLE);
        }else {
            holder.bookmarkIv.setVisibility(View.INVISIBLE);
        }
        final ChapterBean.Chapter chapter = mChapterList.get(position);
        holder.nameTv.setText(chapter.getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ContentAbstractActivity.class);
                intent.putExtra(IntentConstants.BOOK_NAME,mComicName);
                intent.putExtra(IntentConstants.CHAPTER_ID,chapter.getId());
                intent.putExtra(IntentConstants.BOOK,mBook);
                intent.putExtra(IntentConstants.CHAPTER_NAME,chapter.getName());
                intent.putExtra(IntentConstants.CHAPTER_LIST,mChapterList);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChapterList.size();
    }


    public void setPosStatus(int posStatus){
        pos = posStatus;
    }


    class ChapterViewHolder extends RecyclerView.ViewHolder {

        private View layout;
        private TextView nameTv;
        private ImageView bookmarkIv;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            nameTv = itemView.findViewById(R.id.chapter_name_tv);
            bookmarkIv = itemView.findViewById(R.id.chapter_bookmark_iv);
        }
    }

}
