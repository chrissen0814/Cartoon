package com.chrissen.cartoon.adapter.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.dao.greendao.Txt;
import com.chrissen.cartoon.dao.manager.TxtDaoManager;

import java.util.List;

import txtreaderlib.ui.HwTxtPlayActivity;

/**
 * Created by chris on 2017/12/12.
 */

public class TxtAdapter extends RecyclerView.Adapter<TxtAdapter.TxtViewHolder> {

    private List<Txt> mFileList;
    private Context mContext;

    public TxtAdapter(List<Txt> fileList, Context context) {
        mFileList = fileList;
        mContext = context;
    }

    @Override
    public TxtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_txt,parent,false);
        return new TxtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TxtViewHolder holder, int position) {
        final Txt file = mFileList.get(position);
        final String fileName = file.getName().split("\\.")[0];
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRect(fileName,color);
        holder.coverIv.setImageDrawable(textDrawable);
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HwTxtPlayActivity.loadTxt(mContext,file);
            }
        });
        holder.overflowIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext,v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_txt_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.popup_txt_delete:
                                new TxtDaoManager().deleteTxt(mFileList.get(holder.getAdapterPosition()));
                                mFileList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFileList.size();
    }

    class TxtViewHolder extends RecyclerView.ViewHolder{
        private View layout;
        private ImageView coverIv;
        private ImageView overflowIv;

        public TxtViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            coverIv = itemView.findViewById(R.id.txt_cover_image_iv);
            overflowIv = itemView.findViewById(R.id.txt_overflow_iv);
        }
    }

}
