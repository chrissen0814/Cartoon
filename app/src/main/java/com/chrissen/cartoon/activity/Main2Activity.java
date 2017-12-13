package com.chrissen.cartoon.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.avos.avoscloud.AVUser;
import com.chrissen.cartoon.CartoonApplication;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.system.SystemActivity;
import com.chrissen.cartoon.activity.user.SignInActivity;
import com.chrissen.cartoon.activity.user.UserInfoActivity;
import com.chrissen.cartoon.adapter.list.DbBookAdapter;
import com.chrissen.cartoon.adapter.list.TxtAdapter;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.manager.BookDaoManager;
import com.chrissen.cartoon.dao.manager.BookNetDaoManager;
import com.chrissen.cartoon.module.model.ImageModel;
import com.chrissen.cartoon.module.model.TxtModel;
import com.chrissen.cartoon.util.ConfigUtil;
import com.chrissen.cartoon.util.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends BaseAbstractActivity implements View.OnClickListener{
    private static final int REQUEST_CODE_SIGN_IN = 2;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private CollapsingToolbarLayout mCollapsingTl;
    private ImageView mImageView;
    private Toolbar mToolbar;
    private RecyclerView mCartoonRv , mTxtRv;
    private TextView mCartoonMoreTv , mTxtMoreTv;

    private TextView mLoginTv;
    private ImageView mHeadIv;

    private DbBookAdapter mDbBookAdapter;
    private TxtAdapter mTxtAdapter;
    private List<Book> mBookList = new ArrayList<>();
    private List<File> mFileList = new ArrayList<>();
    private LinearLayoutManager mCartoonLm , mTxtLm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initStatus();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initNavi();
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

    private void initNavi() {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            mHeadIv.setVisibility(View.VISIBLE);
            mLoginTv.setVisibility(View.INVISIBLE);
            String userName = user.getUsername();
            TextDrawable nameDrawable = TextDrawable.builder()
                    .beginConfig()
                    .textColor(R.color.colorPrimary)
                    .endConfig()
                    .buildRound(userName.substring(0,1),Color.WHITE);
            mHeadIv.setImageDrawable(nameDrawable);
        }else {
            mHeadIv.setVisibility(View.INVISIBLE);
            mLoginTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initViews() {
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mNavigationView = findViewById(R.id.main_navi_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navi_type:
                        CartoonActivity.actionStart(Main2Activity.this,0);
                        break;
                    case R.id.navi_search:
                        CartoonActivity.actionStart(Main2Activity.this,1);
                        break;
                    case R.id.navi_txt:
                        CartoonActivity.actionStart(Main2Activity.this,2);
                        break;
                }
                return true;
            }
        });
        mCollapsingTl = findViewById(R.id.main_collapsing_ctl);
        mImageView = findViewById(R.id.main_picture_iv);
        mToolbar = findViewById(R.id.main_toolbar);
        mToolbar.setNavigationIcon(R.drawable.toolbar_menu);
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        mToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.icon_overflow));
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.toolbar_setting:
                        break;
                    case R.id.toolbar_about:
                        SystemActivity.actionStart(Main2Activity.this,1);
                        break;
                }
                return true;
            }
        });
        mCartoonRv = findViewById(R.id.main_cartoon_rv);
        mTxtRv = findViewById(R.id.main_txt_rv);
        mCartoonMoreTv = findViewById(R.id.main_cartoon_more_tv);
        mCartoonMoreTv.setOnClickListener(this);
        mTxtMoreTv = findViewById(R.id.main_txt_more_tv);
        mTxtMoreTv.setOnClickListener(this);
        View headView = mNavigationView.getHeaderView(0).getRootView();
        mLoginTv = headView.findViewById(R.id.main_login_tv);
        mLoginTv.setOnClickListener(this);
        mHeadIv = headView.findViewById(R.id.main_header_iv);
        mHeadIv.setOnClickListener(this);
    }

    @Override
    protected void initParams() {
        loadPicture(mImageView);
        mBookList = new BookDaoManager().queryAllBook();
        mDbBookAdapter = new DbBookAdapter(this,mBookList);
        mCartoonLm = new LinearLayoutManager(mAct,LinearLayoutManager.HORIZONTAL,false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mCartoonRv.setLayoutManager(mCartoonLm);
        mCartoonRv.setAdapter(mDbBookAdapter);
        mFileList = new TxtModel().readFiles();
        mTxtAdapter = new TxtAdapter(mFileList,this);
        mTxtLm = new LinearLayoutManager(mAct,LinearLayoutManager.HORIZONTAL,false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mTxtRv.setLayoutManager(mTxtLm);
        mTxtRv.setAdapter(mTxtAdapter);
    }

    private void loadPicture(ImageView imageView) {
        File appDir = CartoonApplication.getContext().getExternalCacheDir();
        File file = new File(appDir, ConfigUtil.BG_IMAGE_NAME);
        if (file != null && file.exists()) {
            ImageUtil.loadImageByFile(file,this,imageView);
        }else {
            ImageUtil.loadImageByRes(R.drawable.main_bg,this,imageView);
        }
        new ImageModel().saveImage();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_cartoon_more_tv:
                break;
            case R.id.main_txt_more_tv:
                break;
            case R.id.main_login_tv:
                Intent intent = new Intent(this,SignInActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SIGN_IN);
                break;
            case R.id.main_header_iv:
                startActivity(new Intent(this, UserInfoActivity.class));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SIGN_IN && resultCode == RESULT_OK){
            BookNetDaoManager.copyBookToDB();
            List<Book> bookList = new BookDaoManager().queryAllBook();
            if (bookList != null && bookList.size() > 0) {
                BookNetDaoManager.copyDBToCloud();
            }
        }
    }


}
