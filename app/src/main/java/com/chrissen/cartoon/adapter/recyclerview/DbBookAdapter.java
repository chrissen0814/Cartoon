package com.chrissen.cartoon.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.ChapterActivity;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.manager.BookDaoManager;
import com.chrissen.cartoon.util.ImageUtil;
import com.chrissen.cartoon.util.IntentConstants;

import java.util.List;

/**
 * Created by chris on 2017/11/17.
 */

public class DbBookAdapter extends RecyclerView.Adapter<DbBookAdapter.DbBookViewHolder> {

    private Context mContext;
    private List<Book> mBookList;

    public DbBookAdapter(Context context) {
        mContext = context;
        mBookList = new BookDaoManager().queryAllBook();
    }

    @Override
    public DbBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_db_book,parent,false);
        return new DbBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DbBookViewHolder holder, int position) {
        final Book book = mBookList.get(position);
        ImageUtil.loadImageByUrl(book.getImageId(),mContext,holder.coverIv);
        holder.nameTv.setText(book.getBookName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChapterActivity.class);
                intent.putExtra(IntentConstants.BOOK_NAME,book.getBookName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    class DbBookViewHolder extends RecyclerView.ViewHolder {
        private View layout;
        private ImageView coverIv;
        private TextView nameTv;

        public DbBookViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            coverIv = itemView.findViewById(R.id.dbbook_cover_iv);
            nameTv = itemView.findViewById(R.id.dbbook_name_tv);
        }
    }

}
