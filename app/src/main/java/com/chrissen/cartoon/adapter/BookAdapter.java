package com.chrissen.cartoon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.util.ImageUtil;

import java.util.List;

/**
 * Created by chris on 2017/11/17.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mContext;
    private List<BookBean.Book> mBookList;


    public BookAdapter(Context context, List<BookBean.Book> bookList) {
        mContext = context;
        mBookList = bookList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        BookBean.Book book = mBookList.get(position);
        holder.nameTv.setText(book.getName());
        holder.areaTv.setText(book.getArea());
        if (book.isFinish()) {
            holder.finishedTv.setText(R.string.book_finished);
            holder.finishedTv.setBackgroundResource(R.drawable.book_finished);
            holder.finishedTv.setTextColor(mContext.getResources().getColor(R.color.book_finished));
        }else {
            holder.finishedTv.setText(R.string.book_unfinished);
            holder.finishedTv.setBackgroundResource(R.drawable.book_unfinished);
            holder.finishedTv.setTextColor(mContext.getResources().getColor(R.color.book_unfinished));
        }
        ImageUtil.loadImageByUrl(book.getCoverImg(),mContext,holder.coverIv);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private View layout;
        private ImageView coverIv;
        private TextView nameTv , areaTv , finishedTv;


        public BookViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            coverIv = itemView.findViewById(R.id.item_book_cover_iv);
            nameTv = itemView.findViewById(R.id.item_book_name_tv);
            areaTv = itemView.findViewById(R.id.item_book_area_tv);
            finishedTv = itemView.findViewById(R.id.item_book_finished_tv);
        }
    }

}
