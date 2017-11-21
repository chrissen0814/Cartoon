package com.chrissen.cartoon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.chrissen.cartoon.CartoonApplication;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.user.SignInActivity;
import com.chrissen.cartoon.activity.user.UserInfoActivity;
import com.chrissen.cartoon.adapter.list.MenuAdapter;
import com.chrissen.cartoon.bean.HitokotoBean;
import com.chrissen.cartoon.fragment.AboutFragment;
import com.chrissen.cartoon.fragment.MainFragment;
import com.chrissen.cartoon.fragment.SearchFragment;
import com.chrissen.cartoon.fragment.SettingFragment;
import com.chrissen.cartoon.fragment.TypeFragment;
import com.chrissen.cartoon.module.model.AcgModel;
import com.chrissen.cartoon.module.model.HitokotoModel;
import com.chrissen.cartoon.util.ConfigUtil;
import com.chrissen.cartoon.util.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener{

    private Toolbar mToolbar;
    private DuoDrawerLayout mDrawerLayout;
    private DuoMenuView mMenuView;
    private ArrayList<String> mTitleList;
    private MenuAdapter mMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initDrawer();

    }


    @Override
    protected void onStart() {
        super.onStart();
        initView();
        initAcg();
    }

    private void initView() {
        startFragment(new MainFragment(),false);
        mMenuAdapter.setViewSelected(0,true);
        setTitle(mTitleList.get(0));
        Button button = mMenuView.getFooterView().findViewById(R.id.footer_bt);
        initHitokoto();
        if (AVUser.getCurrentUser() != null) {
            button.setText(R.string.navi_user_info);
        }else {
            button.setText(R.string.navi_register_sign_in);
        }
    }


    private void initHitokoto(){
        final TextView textTv = mMenuView.getHeaderView().findViewById(R.id.header_text_tv);
        final boolean hitokoto = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("setting_hitokoto",false);
        if (hitokoto) {
            new HitokotoModel().getHitokoto(new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == ConfigUtil.SUCCESS_MSG) {
                        HitokotoBean hitokotoBean = (HitokotoBean) msg.obj;
                        if (hitokotoBean != null) {
                            textTv.setText(hitokotoBean.getHitokoto());
                        }else {
                            textTv.setText(R.string.default_header_text);
                        }
                    }else {
                        textTv.setText(R.string.default_header_text);
                    }
                }
            });
        }else {
            textTv.setText(R.string.default_header_text);
        }
    }

    private void initDrawer() {
        DuoDrawerToggle toggle = new DuoDrawerToggle(this,mDrawerLayout,mToolbar,R.string.navi_open,R.string.navi_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mTitleList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.navi_title)));
        mMenuAdapter = new MenuAdapter(mTitleList);
        mMenuView.setAdapter(mMenuAdapter);
        mMenuView.setOnMenuClickListener(this);
        setTitle(mTitleList.get(0));

    }

    private void initAcg() {
        boolean acg = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("setting_navi_bg",false);
        ImageView imageView = mMenuView.findViewById(R.id.duo_view_menu_background);
        if (acg) {
            File appDir = CartoonApplication.getContext().getExternalCacheDir();
            File file = new File(appDir,ConfigUtil.BG_IMAGE_NAME);
            if (file != null && file.exists()) {
                ImageUtil.loadBlurImageByFile(file,this,imageView);
            }else {
                ImageUtil.loadBlurImageByRes(R.drawable.main_bg,this,imageView);
            }
            new AcgModel().saveAcg();
        }else {
            imageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

    }


    private void findViews() {
        mDrawerLayout = findViewById(R.id.main_duo_drawer_layout);
        mMenuView = findViewById(R.id.main_duo_menu_view);
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
    }


    @Override
    public void onFooterClicked() {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser != null) {
            startActivity(new Intent(this, UserInfoActivity.class));
        }else {
            startActivity(new Intent(this, SignInActivity.class));
        }
    }

    @Override
    public void onHeaderClicked() {

    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        setTitle(mTitleList.get(position));
        mMenuAdapter.setViewSelected(position,true);

        switch (position){
            case 0:
                startFragment(new MainFragment(),false);
                break;
            case 1:
                startFragment(new TypeFragment(),false);
                break;
            case 2:
                startFragment(new SearchFragment(),false);
                break;
            case 3:
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_container,new SettingFragment())
                        .commit();
                break;
            case 4:
                startFragment(new AboutFragment(),false);
                break;
        }
        mDrawerLayout.closeDrawer();
    }

    private void startFragment(Fragment fragment , boolean addToBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(R.id.main_container, fragment).commit();

    }


}
