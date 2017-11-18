package com.chrissen.cartoon.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.ContentActivity;
import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.util.IntentConstants;

import java.util.List;

/**
 * Created by chris on 2017/11/18.
 */

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private Context mContext;
    private String mComicName;
    private List<ChapterBean.Chapter> mChapterList;

    public ChapterAdapter(Context context, String comicName, List<ChapterBean.Chapter> chapterList) {
        mContext = context;
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
        final ChapterBean.Chapter chapter = mChapterList.get(position);
        holder.nameTv.setText(chapter.getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ContentActivity.class);
                intent.putExtra(IntentConstants.BOOK_NAME,mComicName);
                intent.putExtra(IntentConstants.CHAPTER_ID,chapter.getId());
                mContext.startActivity(intent);
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

        public ChapterViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            nameTv = itemView.findViewById(R.id.chapter_name_tv);
        }
    }

}
