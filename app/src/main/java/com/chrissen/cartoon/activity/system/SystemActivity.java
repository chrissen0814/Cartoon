package com.chrissen.cartoon.activity.system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.BaseAbstractActivity;
import com.chrissen.cartoon.fragment.AboutFragment;
import com.chrissen.cartoon.util.IntentConstants;

public class SystemActivity extends BaseAbstractActivity {

    private int mFragmentType;


    /**
     *
     * @param context
     * @param fragmentType 0 : setting , 1:about
     */
    public static void actionStart(Context context , int fragmentType){
        Intent intent = new Intent(context,SystemActivity.class);
        intent.putExtra(IntentConstants.SYSTEM_FRAGMENT_TYPE,fragmentType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        initParams();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initParams() {
        mFragmentType = getIntent().getIntExtra(IntentConstants.SYSTEM_FRAGMENT_TYPE,-1);
        if (mFragmentType == 0) {

        }else if(mFragmentType == 1){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.system_container_fl,new AboutFragment())
                    .commit();
        }
    }
}
