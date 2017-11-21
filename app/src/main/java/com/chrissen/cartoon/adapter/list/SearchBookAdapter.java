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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.util.ImageUtil;
import com.chrissen.cartoon.util.ScreenUtil;

import java.util.List;

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
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.mImageIv.getLayoutParams();
        LinearLayout.LayoutParams lps = (LinearLayout.LayoutParams) holder.mNameTv.getLayoutParams();
        int rowWidth = ScreenUtil.getScreenWidth() / mSpanCount;
        lp.width = rowWidth;
        lps.width = rowWidth;
        holder.mImageIv.setLayoutParams(lp);
        holder.mNameTv.setLayoutParams(lps);
        BookBean.Book book = mBookList.get(position);
        holder.mNameTv.setText(book.getName());

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

        public SearchBookViewHodler(View itemView) {
            super(itemView);
            layout = itemView;
            mLayout = itemView.findViewById(R.id.search_book_ll);
            mImageIv = itemView.findViewById(R.id.search_book_image_iv);
            mNameTv = itemView.findViewById(R.id.search_book_name_tv);
        }
    }

}
