package com.chrissen.cartoon.activity.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.dao.manager.BookDaoManager;

public class UserInfoActivity extends AppCompatActivity {

    private ImageView mBgIv;
    private TextView mNameTv , mEmailTv , mBookTv , mTimeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        findViews();
        initParams();
    }

    private void initParams() {
        String name = AVUser.getCurrentUser().getUsername();
        String email = AVUser.getCurrentUser().getEmail();
        String book = "您拥有" + new BookDaoManager().queryAllBook().size() + "本书";
        mNameTv.setText(name);
        mEmailTv.setText(email);
        mBookTv.setText(book);
    }

    private void findViews() {
        mBgIv = findViewById(R.id.user_info_bg_iv);
        mNameTv = findViewById(R.id.user_info_name_tv);
        mEmailTv = findViewById(R.id.user_info_email_tv);
        mBookTv = findViewById(R.id.user_info_book_tv);
        mTimeTv = findViewById(R.id.user_info_time_tv);
    }

    public void onLogoutClick(View view) {
        AVUser.logOut();
        finish();
    }

    public void onBackClick(View view) {
        finish();
    }
}
