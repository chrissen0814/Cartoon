package com.chrissen.cartoon.module.presenter.type;

import com.chrissen.cartoon.module.model.TypeModel;
import com.chrissen.cartoon.module.view.BaseView;
import com.chrissen.cartoon.module.view.TypeView;

import java.util.List;

/**
 * Created by chris on 2017/11/16.
 */

public class TypePresenter implements OnTypeListener {
    private TypeModel mTypeModel;
    private BaseView mBaseView;


    public TypePresenter( TypeView view){
        mBaseView = view;
        mTypeModel = new TypeModel();
    }

    public void getComicType(){
        mTypeModel.getComicType(this);
    }


    @Override
    public void onFail(String errorMsg) {
        mBaseView.onShowError(errorMsg);
    }

    @Override
    public void onSuccess(List<String> typeList) {
        mBaseView.onShowSuccess(typeList);
    }

}
