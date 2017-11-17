package com.chrissen.cartoon.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.module.presenter.type.TypePresenter;
import com.chrissen.cartoon.module.view.TypeView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TypeView {

    private TypePresenter mPresenter;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.main_text_tv);
        mPresenter = new TypePresenter(this);
        mPresenter.getComicType();
    }



    @Override
    public void onShowSuccess(List<String> obj) {
        String type = obj.get(0) + obj.get(1);
        mTextView.setText(type);
    }

    @Override
    public void onShowError(String errorMsg) {

    }
}
