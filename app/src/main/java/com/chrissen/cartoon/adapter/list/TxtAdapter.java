package com.chrissen.cartoon.adapter.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.chrissen.cartoon.R;
import com.hw.txtreaderlib.ui.HwTxtPlayActivity;

import java.io.File;
import java.util.List;

/**
 * Created by chris on 2017/12/12.
 */

public class TxtAdapter extends RecyclerView.Adapter<TxtAdapter.TxtViewHolder> {

    private List<File> mFileList;
    private Context mContext;

    public TxtAdapter(List<File> fileList, Context context) {
        mFileList = fileList;
        mContext = context;
    }

    @Override
    public TxtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_txt,parent,false);
        return new TxtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TxtViewHolder holder, int position) {
        final File file = mFileList.get(position);
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
                HwTxtPlayActivity.LoadTxtFile(mContext, file.getAbsolutePath(),fileName.split("\\.")[0]);
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

        public TxtViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            coverIv = itemView.findViewById(R.id.txt_cover_image_iv);
        }
    }

}
