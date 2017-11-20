package com.chrissen.cartoon.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.list.MenuAdapter;
import com.chrissen.cartoon.fragment.MainFragment;
import com.chrissen.cartoon.fragment.SearchFragment;
import com.chrissen.cartoon.fragment.TypeFragment;
import com.chrissen.cartoon.util.ConfigUtil;

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
        initView();
        initAcg();
    }

    private void initView() {
        startFragment(new MainFragment(),false);
        mMenuAdapter.setViewSelected(0,true);
        setTitle(mTitleList.get(0));
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
        File appDir = new File(Environment.getExternalStorageDirectory(), ConfigUtil.APP_DIR);
        File file = new File(appDir,ConfigUtil.BG_IMAGE_NAME);
        if (file != null && file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
//            mMenuView.setBackground(new GlideBitmapDrawable(null,bitmap));
        }else {
//            mMenuView.setBackground(R.drawable.main_bg);
        }
//        new AcgModel().saveAcg();
    }


    private void findViews() {
        mDrawerLayout = findViewById(R.id.main_duo_drawer_layout);
        mMenuView = findViewById(R.id.main_duo_menu_view);
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
    }


    @Override
    public void onFooterClicked() {

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

                break;
            case 4:
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
