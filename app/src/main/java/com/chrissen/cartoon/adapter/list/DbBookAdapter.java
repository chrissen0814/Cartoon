package com.chrissen.cartoon.adapter.list;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.ChapterActivity;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.manager.BookDaoManager;
import com.chrissen.cartoon.util.ImageUtil;
import com.chrissen.cartoon.util.IntentConstants;
import com.chrissen.cartoon.util.ScreenUtil;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by chris on 2017/11/17.
 */

public class DbBookAdapter extends RecyclerView.Adapter<DbBookAdapter.DbBookViewHolder> {

    private Context mContext;
    private List<Book> mBookList;
    private int spanCount = 2;
    private Dialog mBottomDialog;
    private int clickedPos;

    public DbBookAdapter(Context context) {
        mContext = context;
        mBookList = new BookDaoManager().queryAllBook();
        initDialog();
    }


    @Override
    public DbBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_db_book,parent,false);
        return new DbBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DbBookViewHolder holder, final int position) {
        int rowWidth = ScreenUtil.getScreenWidth()/spanCount;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.coverIv.getLayoutParams();
        lp.width = rowWidth;
        holder.coverIv.setLayoutParams(lp);
        final Book book = mBookList.get(position);
        ImageUtil.loadImageByUrl(book.getImageId(),mContext,holder.coverIv);
        holder.nameTv.setText(book.getBookName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChapterActivity.class);
                intent.putExtra(IntentConstants.BOOK_NAME,book.getBookName());
                intent.putExtra(IntentConstants.BOOK,book);
                mContext.startActivity(intent);
            }
        });
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickedPos = holder.getAdapterPosition();
                showOrHideDialog(true);
                return true;
            }
        });
    }

    private void initDialog() {
        mBottomDialog = new Dialog(mContext, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_book_edit, null);
        TextView addNoteTv = contentView.findViewById(R.id.bottom_dialog_add_note_tv);
        TextView deleteTv = contentView.findViewById(R.id.bottom_dialog_delete_tv);
        addNoteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BookDaoManager().deleteBook(mBookList.get(clickedPos));
                mBookList.remove(clickedPos);
                notifyItemRemoved(clickedPos);
                showOrHideDialog(false);
                Toasty.success(mContext,"删除成功", Toast.LENGTH_SHORT,true).show();
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

    private void showOrHideDialog(boolean flag) {
        if (flag) {
            mBottomDialog.show();
        }else {
            mBottomDialog.cancel();
        }
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
