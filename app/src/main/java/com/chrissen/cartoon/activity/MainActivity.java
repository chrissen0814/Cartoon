package com.chrissen.cartoon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.module.presenter.type.TypePresenter;
import com.chrissen.cartoon.module.view.TypeView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TypeView {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private TypePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mPresenter = new TypePresenter(this);
        mPresenter.getComicType();
    }

    private void initViews() {
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mNavigationView = findViewById(R.id.main_navigation_view);
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.toolbar_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
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
    public void onShowSuccess(List<String> obj) {
    }

    @Override
    public void onShowError(String errorMsg) {

    }
}
