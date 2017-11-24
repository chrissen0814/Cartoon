package com.chrissen.cartoon.adapter.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.manager.BookDaoManager;
import com.chrissen.cartoon.dao.manager.BookNetDaoManager;
import com.chrissen.cartoon.util.AnimUtil;
import com.chrissen.cartoon.util.ImageUtil;
import com.chrissen.cartoon.util.ScreenUtil;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by chris on 2017/11/21.
 */

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.SearchBookViewHodler> {

    private List<BookBean.Book> mBookList;
    private Context mContext;
    private int mSpanCount;

    public SearchBookAdapter(List<BookBean.Book> bookList, Context context , int spanCount) {
        mBookList = bookList;
        mContext = context;
        mSpanCount = spanCount;
    }

    @Override
    public SearchBookViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_book,parent,false);
        return new SearchBookViewHodler(view);
    }

    @Override
    public void onBindViewHolder(final SearchBookViewHodler holder, int position) {
        final BookDaoManager bookDaoManager = new BookDaoManager();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.mImageIv.getLayoutParams();
        LinearLayout.LayoutParams lps = (LinearLayout.LayoutParams) holder.mNameTv.getLayoutParams();
        int rowWidth = ScreenUtil.getScreenWidth() / mSpanCount;
        lp.width = rowWidth;
        lps.width = rowWidth;
        holder.mImageIv.setLayoutParams(lp);
        holder.mNameTv.setLayoutParams(lps);
        final BookBean.Book book = mBookList.get(position);
        holder.mNameTv.setText(book.getName());
        final boolean collected = bookDaoManager.judgeExist(book.getType(),book.getName(),book.getArea());
        if (collected) {
            holder.mBookmarkIv.setVisibility(View.VISIBLE);
        }else {
            holder.mBookmarkIv.setVisibility(View.INVISIBLE);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Shake)
                        .duration(500)
                        .playOn(v);
                if (holder.mBookmarkIv.getVisibility() == View.VISIBLE) {
                    Book savedBook = bookDaoManager.queryBookByBean(book);
                    if (savedBook != null) {
                        AnimUtil.slideOutFromUp(holder.mBookmarkIv,mContext);
                        if (AVUser.getCurrentUser() != null) {
                            BookNetDaoManager.deleteBook(savedBook);
                        }
                        bookDaoManager.deleteBook(savedBook);
                        Toasty.success(mContext,mContext.getString(R.string.toast_search_delete), Toast.LENGTH_SHORT,false).show();
                    }
                }else {
                    AnimUtil.slideInFromUp(holder.mBookmarkIv,mContext);
                    Toasty.success(mContext,mContext.getString(R.string.toast_search_add),Toast.LENGTH_SHORT,false).show();
                    bookDaoManager.addBook(book);
                    if (AVUser.getCurrentUser() != null) {
                        BookNetDaoManager.saveBook(bookDaoManager.queryBookByBean(book),null);
                    }
                }
            }
        });
        Glide.with(mContext).load(book.getCoverImg())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Bitmap bitmap = ImageUtil.drawableToBitmap(resource);
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@NonNull Palette palette) {
                                Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                                Palette.Swatch darkMutedSwatch  = palette.getDarkMutedSwatch();
                                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                                if (darkVibrantSwatch != null) {
                                    holder.mNameTv.setBackgroundColor(darkVibrantSwatch.getRgb());
                                }else if(darkMutedSwatch != null) {
                                    holder.mNameTv.setBackgroundColor(darkMutedSwatch.getRgb());
                                }else if(vibrantSwatch != null){
                                    holder.mNameTv.setBackgroundColor(vibrantSwatch.getRgb());
                                }else {
                                    holder.mNameTv.setBackgroundColor(Color.WHITE);
                                    holder.mNameTv.setTextColor(Color.BLACK);
                                }
                            }
                        });
                        return false;
                    }
                }).into(holder.mImageIv);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    class SearchBookViewHodler extends RecyclerView.ViewHolder {
        private View layout;
        private LinearLayout mLayout;
        private ImageView mImageIv;
        private TextView mNameTv;
        private ImageView mBookmarkIv;

        public SearchBookViewHodler(View itemView) {
            super(itemView);
            layout = itemView;
            mLayout = itemView.findViewById(R.id.search_book_ll);
            mImageIv = itemView.findViewById(R.id.search_book_image_iv);
            mNameTv = itemView.findViewById(R.id.search_book_name_tv);
            mBookmarkIv = itemView.findViewById(R.id.search_book_mark_iv);
        }
    }

}
