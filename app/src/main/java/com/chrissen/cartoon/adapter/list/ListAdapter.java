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

import java.util.ArrayList;

/**
 * Created by chris on 2017/11/22.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ChapterViewHolder> {

    private OnListItemClickListener mListener;
    private Context mContext;
    private String mComicName , mChapterId;
    private ArrayList<ChapterBean.Chapter> mChapterList;

    private int pos;

    public ListAdapter(Context context , String comicName, String chapterId , ArrayList<ChapterBean.Chapter> chapterList) {
        mContext = context;
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
    public void onBindViewHolder(final ChapterViewHolder holder, int position) {

        final ChapterBean.Chapter chapter = mChapterList.get(position);
//        if (chapter.getId().equals(mChapterId)) {
//            holder.dotIv.setVisibility(View.VISIBLE);
//        }else {
//            holder.dotIv.setVisibility(View.GONE);
//        }
        if(pos == position){
            holder.dotIv.setVisibility(View.VISIBLE);
        }else {
            holder.dotIv.setVisibility(View.GONE);
        }
        holder.nameTv.setText(chapter.getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListItemClick(v,holder.getAdapterPosition());
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

    public interface OnListItemClickListener{
        void onListItemClick(View view , int position);
    }

    public void setOnListItemClickListener(OnListItemClickListener listener){
        mListener = listener;
    }

    public void setCurPos(int pos){
        this.pos = pos;
    }

    public int getPos(){
        return pos;
    }

}
