package com.chrissen.cartoon.adapter.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.dao.greendao.Book;

import java.util.List;

/**
 * Created by chris on 2017/11/22.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ChapterViewHolder> {

    private Context mContext;
    private String mComicName , mChapterId;
    private List<ChapterBean.Chapter> mChapterList;
    private Book mBook;

    private int pos = -1;

    public ListAdapter(Context context, Book book , String comicName,String chapterId , List<ChapterBean.Chapter> chapterList) {
        mContext = context;
        mBook = book;
        mComicName = comicName;
        mChapterList = chapterList;
        mChapterId = chapterId;
    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chapter_list,parent,false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {

        final ChapterBean.Chapter chapter = mChapterList.get(position);
        if (chapter.getId().equals(mChapterId)) {
            holder.dotIv.setVisibility(View.VISIBLE);
        }else {
            holder.dotIv.setVisibility(View.GONE);
        }
        holder.nameTv.setText(chapter.getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mChapterList.size();
    }


    class ChapterViewHolder extends RecyclerView.ViewHolder {

        private View layout;
        private TextView nameTv;
        private ImageView dotIv;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            nameTv = itemView.findViewById(R.id.chapter_list_name_tv);
            dotIv = itemView.findViewById(R.id.chapter_list_dot_iv);
        }
    }

}
