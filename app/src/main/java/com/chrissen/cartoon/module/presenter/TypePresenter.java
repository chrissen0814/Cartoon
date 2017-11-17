package com.chrissen.cartoon.module.presenter;

import com.chrissen.cartoon.module.model.TypeModel;
import com.chrissen.cartoon.module.view.TypeView;

import java.util.List;

/**
 * Created by chris on 2017/11/16.
 */

public class TypePresenter implements OnTypeListener {
    private TypeModel mTypeModel;
    private TypeView mTypeView;


    public TypePresenter( TypeView view){
        mTypeView = view;
        mTypeModel = new TypeModel();
    }

    public void getComicType(){
        mTypeModel.getComicType(this);
    }


    @Override
    public void onSuccess(List<String> typeList) {
        mTypeView.onSuccess(typeList);
    }

    @Override
    public void onError(String errorMsg) {
        mTypeView.onError(errorMsg);
    }
}
