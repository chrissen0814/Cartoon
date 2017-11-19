package com.chrissen.cartoon.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.recyclerview.DbBookAdapter;
import com.chrissen.cartoon.bean.HitokotoBean;
import com.chrissen.cartoon.module.model.AcgModel;
import com.chrissen.cartoon.module.model.HitokotoModel;
import com.chrissen.cartoon.util.ConfigUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private View mNaviHeadView;
    private RecyclerView mRecyclerView;
    private CardView mCardView;
    private TextView mHitokotoTv , mHitokotoFromTv;

    private DbBookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initHitokoto();
        initAcg();
    }

    private void initAcg() {
//        new AcgModel().getHitokoto(new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what == ConfigUtil.SUCCESS_MSG) {
//                    AcgBean acgBean = (AcgBean) msg.obj;
//                    ImageUtil.loadImageByUrl(acgBean.getImgUrl(),MainActivity.this, (ImageView) mNaviHeadView.findViewById(R.id.main_navi_head_iv));
//                }else if(msg.what == ConfigUtil.FAIL_MSG){
//                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        ImageView imageView = mNaviHeadView.findViewById(R.id.main_navi_head_iv);
        File appDir = new File(Environment.getExternalStorageDirectory(), ConfigUtil.APP_DIR);
        File file = new File(appDir,ConfigUtil.BG_IMAGE_NAME);
        if (file != null && file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
            imageView.setImageBitmap(bitmap);
        }else {
            imageView.setImageResource(R.mipmap.ic_launcher);
        }
        new AcgModel().saveAcg();
    }

    private void initHitokoto() {
        new HitokotoModel().getHitokoto(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == ConfigUtil.SUCCESS_MSG) {
                    HitokotoBean hitokotoBean = (HitokotoBean) msg.obj;
                    mHitokotoTv.setText(hitokotoBean.getHitokoto());
                    mHitokotoFromTv.setText(hitokotoBean.getFrom());
                }else {

                }
            }
        });
    }

    private void initViews() {
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mNavigationView = findViewById(R.id.main_navigation_view);
        mToolbar = findViewById(R.id.main_toolbar);
        mRecyclerView = findViewById(R.id.main_rv);
        mCardView = findViewById(R.id.main_hitokoto_layout_cv);
        mHitokotoTv = findViewById(R.id.main_hitokoyo_tv);
        mHitokotoFromTv = findViewById(R.id.main_hitokoyo_from_tv);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.toolbar_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        mNaviHeadView = mNavigationView.getHeaderView(0);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navi_main:
                        break;
                    case R.id.navi_type:
                        startActivity(new Intent(MainActivity.this,TypeActivity.class));
                        break;
                    case R.id.navi_search:
                        break;
                    case R.id.navi_setting:
                        break;
                    case R.id.navi_about:
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new DbBookAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
