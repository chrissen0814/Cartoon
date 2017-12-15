package com.chrissen.cartoon.adapter.list;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.BaseAbstractActivity;
import com.chrissen.cartoon.activity.BookDetailActivity;
import com.chrissen.cartoon.activity.BookNoteActivity;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.manager.BookDaoManager;
import com.chrissen.cartoon.dao.manager.BookNetDaoManager;
import com.chrissen.cartoon.util.ImageUtil;
import com.chrissen.cartoon.util.IntentConstants;
import com.chrissen.cartoon.util.ScreenUtil;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by chris on 2017/11/17.
 */

public class DbBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_EMPTY = 0;
    private static final int VIEW_ITEM = 1;

    private Context mContext;
    private List<Book> mBookList;
    private int spanCount = 2;
    private Dialog mBottomDialog;
    private int clickedPos;

    public DbBookAdapter(Context context, List<Book> bookList) {
        mContext = context;
        mBookList = bookList;
        initDialog();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_EMPTY) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view,parent,false);
            return new EmptyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_db_book,parent,false);
            return new DbBookViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DbBookViewHolder) {
            DbBookViewHolder dbBookViewHolder = (DbBookViewHolder) holder;
            int rowWidth = ScreenUtil.getScreenWidth()/spanCount;
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dbBookViewHolder.coverIv.getLayoutParams();
            lp.width = rowWidth;
            dbBookViewHolder.coverIv.setLayoutParams(lp);
            final Book book = mBookList.get(position);
            ImageUtil.loadImageByUrl(book.getImageId(),mContext,dbBookViewHolder.coverIv);
            dbBookViewHolder.nameTv.setText(book.getBookName());
            dbBookViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, ChapterActivity.class);
//                    intent.putExtra(IntentConstants.BOOK_NAME,book.getBookName());
//                    intent.putExtra(IntentConstants.BOOK,book);
//                    mContext.startActivity(intent);
                    ((BaseAbstractActivity)mContext).putBindClick(v);
                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra(IntentConstants.BOOK,mBookList.get(holder.getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
            dbBookViewHolder.layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickedPos = holder.getAdapterPosition();
                    showOrHideDialog(true);
                    return true;
                }
            });
        }else if(holder instanceof EmptyViewHolder){
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
            ConstraintLayout.LayoutParams lps = (ConstraintLayout.LayoutParams) emptyViewHolder.mEmptyImageIv.getLayoutParams();
            lps.width = ScreenUtil.getScreenWidth();
            emptyViewHolder.mEmptyImageIv.setLayoutParams(lps);
            emptyViewHolder.layout.setMinimumWidth(ScreenUtil.getScreenWidth());
        }

    }

    private void initDialog() {
        mBottomDialog = new Dialog(mContext, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_book_edit, null);
        TextView addNoteTv = contentView.findViewById(R.id.bottom_dialog_add_note_tv);
        TextView deleteTv = contentView.findViewById(R.id.bottom_dialog_delete_tv);
        TextView detailTv = contentView.findViewById(R.id.bottom_dialog_detail_tv);
        addNoteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseAbstractActivity)mContext).putBindClick(v);
                Intent intent = new Intent(mContext, BookNoteActivity.class);
                intent.putExtra(IntentConstants.BOOK,mBookList.get(clickedPos));
                mContext.startActivity(intent);
                showOrHideDialog(false);
            }
        });
        detailTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseAbstractActivity)mContext).putBindClick(v);
                Intent intent = new Intent(mContext, BookDetailActivity.class);
                intent.putExtra(IntentConstants.BOOK,mBookList.get(clickedPos));
                mContext.startActivity(intent);
                showOrHideDialog(false);
            }
        });
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseAbstractActivity)mContext).putBindClick(v);
                notifyItemRemoved(clickedPos);
                showOrHideDialog(false);
                Toasty.success(mContext,"删除成功", Toast.LENGTH_SHORT,true).show();
                if (AVUser.getCurrentUser() != null) {
                    BookNetDaoManager.deleteBook(mBookList.get(clickedPos));
                }
                new BookDaoManager().deleteBook(mBookList.get(clickedPos));
                mBookList.remove(clickedPos);
                ((BaseAbstractActivity)mContext).resetClick();
            }
        });
        mBottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = ScreenUtil.getScreenWidth() - ScreenUtil.dip2px(mContext,16f);
        params.bottomMargin = ScreenUtil.dip2px(mContext,8f);
        contentView.setLayoutParams(params);
        mBottomDialog.setCanceledOnTouchOutside(true);
        mBottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
    }

    /**
     *
     * @param flag true:show false:hide
     */
    private void showOrHideDialog(boolean flag) {
        if (flag) {
            mBottomDialog.show();
        }else {
            mBottomDialog.cancel();
        }
    }

    @Override
    public int getItemCount() {
        if (mBookList.size() == 0) {
            return 1;
        }else {
            return mBookList.size();
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (mBookList.size() == 0) {
            return VIEW_EMPTY;
        }else {
            return VIEW_ITEM;
        }
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

    class EmptyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mEmptyImageIv;
        private View layout;
        public EmptyViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            mEmptyImageIv = itemView.findViewById(R.id.empty_image_iv);
        }
    }

}
