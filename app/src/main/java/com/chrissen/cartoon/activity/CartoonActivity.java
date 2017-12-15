package com.chrissen.cartoon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.dao.manager.TxtDaoManager;
import com.chrissen.cartoon.fragment.SearchFragment;
import com.chrissen.cartoon.fragment.TypeFragment;
import com.chrissen.cartoon.util.IntentConstants;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.util.regex.Pattern;

public class CartoonActivity extends BaseAbstractActivity {
    private static final int ADD_FILE = 5;
    private int mFragmentType;


    /**
     *
     * @param context
     * @param fragmentType 0 : kind , 1:search , 2:txt
     */
    public static void actionStart(Context context , int fragmentType){
        Intent intent = new Intent(context,CartoonActivity.class);
        intent.putExtra(IntentConstants.CARTOON_FRAGMENT_TYPE,fragmentType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon);
        initParams();
        initViews();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initParams() {
        mFragmentType = getIntent().getIntExtra(IntentConstants.CARTOON_FRAGMENT_TYPE,-1);
        if (mFragmentType == 0) {
           getSupportFragmentManager().beginTransaction()
                   .replace(R.id.cartoon_container_fl,new TypeFragment())
                   .commit();
        }else if(mFragmentType == 1){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cartoon_container_fl,new SearchFragment())
                    .commit();
        }else if(mFragmentType == 2){
            new MaterialFilePicker().withActivity(this)
                    .withFilter(Pattern.compile(".*\\.txt$"))
                    .withRequestCode(ADD_FILE)
                    .start();
        }
    }

    public void onBackClick(View view) {
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FILE && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(filePath);
            if (!new TxtDaoManager().judgeExist(filePath)) {
                new TxtDaoManager().addTxt(file);
            }
            finish();
        }
    }

}
