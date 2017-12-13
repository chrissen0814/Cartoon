package com.chrissen.cartoon.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.util.ImageUtil;
import com.chrissen.cartoon.util.IntentConstants;
import com.chrissen.cartoon.util.SystemUtil;

public class BookDetailAbstractActivity extends BaseAbstractActivity {

    private static final int BOOK_ADD_COMMENT = 2;

    private ImageView mImageIv;
    private Toolbar mToolbar;
    private CardView mCommentCv;
    private ImageView mAddCommentIv;
    private TextView mAddedTimeTv , mUpdatedTimeTv;
    private TextView mTypeTv , mAreaTv;
    private TextView mCommentTv;

    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initStatus();
        initViews();
        initParams();
    }


    private void initStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


    protected void initParams() {
        mBook = (Book) getIntent().getSerializableExtra(IntentConstants.BOOK);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle(mBook.getBookName());
        ImageUtil.loadImageByUrl(mBook.getImageId(),this,mImageIv);
        mTypeTv.setText(mBook.getType());
        mAreaTv.setText(mBook.getArea());
        mAddedTimeTv.setText(SystemUtil.timeStampToStr(mBook.getAddedTime()));
        mUpdatedTimeTv.setText(SystemUtil.timeStampToStr(mBook.getUpdatedTime()));
        if (mBook.getComment() != null && !mBook.getComment().equals(" ")) {
            mAddCommentIv.setVisibility(View.GONE);
            mCommentTv.setText(mBook.getComment());
        }else {
            mAddCommentIv.setVisibility(View.VISIBLE);
            mAddCommentIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookDetailAbstractActivity.this,BookNoteActivity.class);
                    intent.putExtra(IntentConstants.BOOK,mBook);
                    intent.putExtra(IntentConstants.FROM_DETAIL,true);
                    startActivityForResult(intent,BOOK_ADD_COMMENT);
                }
            });
        }
    }

    protected void initViews() {
        mImageIv = findViewById(R.id.detail_image_iv);
        mToolbar = findViewById(R.id.detail_toolbar);
        mAddedTimeTv = findViewById(R.id.detail_add_time_tv);
        mUpdatedTimeTv = findViewById(R.id.detail_update_time_tv);
        mTypeTv = findViewById(R.id.detail_type_tv);
        mAreaTv = findViewById(R.id.detail_area_tv);
        mCommentTv = findViewById(R.id.detail_comment_tv);
        mCommentCv = findViewById(R.id.book_detail_comment_cv);
        mAddCommentIv = findViewById(R.id.detail_comment_add_iv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case BOOK_ADD_COMMENT:
                if (resultCode == RESULT_OK) {
                    String comment = data.getStringExtra(IntentConstants.BOOK_NOTE);
                    if (comment != null && !comment.equals(" ")) {
                        mAddCommentIv.setVisibility(View.GONE);
                        mCommentTv.setVisibility(View.VISIBLE);
                        mCommentTv.setText(comment);
                    }
                }
                break;
        }
    }
}
