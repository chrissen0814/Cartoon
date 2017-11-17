package com.chrissen.cartoon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.BookActivity;
import com.chrissen.cartoon.util.IntentConstants;
import com.chrissen.cartoon.util.transition.EasyTransition;
import com.chrissen.cartoon.util.transition.EasyTransitionOptions;

/**
 * Created by chris on 2017/11/17.
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder> {

    private Context mContext;
    private String[] mTypes;

    public TypeAdapter(Context context ,String[] types) {
        mContext = context;
        mTypes = types;
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_type,parent,false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TypeViewHolder holder, final int position) {
        holder.nameTv.setText(mTypes[position]);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyTransitionOptions options = EasyTransitionOptions.makeTransitionOptions(
                        (Activity) mContext,
                        v.findViewById(R.id.book_type));
                Intent intent = new Intent(mContext, BookActivity.class);
                intent.putExtra(IntentConstants.BOOK_TYPE,mTypes[position]);
                EasyTransition.startActivity(intent,options);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTypes.length;
    }

    class TypeViewHolder extends RecyclerView.ViewHolder {
        private View layout;
        private TextView nameTv;

        public TypeViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            nameTv = itemView.findViewById(R.id.book_type);
        }
    }

}
