package com.chrissen.cartoon.dao;

import com.chrissen.cartoon.util.NetworkCallback;
import com.chrissen.cartoon.util.Response;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chris on 2017/11/15.
 */

public abstract class BaseNetworkDao<T> {

    protected T api;

    protected abstract T createApi();


    public BaseNetworkDao(){
        api = createApi();
        if (api == null) {
            throw new RuntimeException("请初始化api");
        }
    }

    protected void doRequest(io.reactivex.Observable observable , final NetworkCallback networkCallback){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response response) {
                        if (networkCallback != null) {
                            if (response == null) {
                                networkCallback.onError(-1, "");
                            } else if (response.error_code == 0) {
                                networkCallback.onSuccess(response.result);
                            } else {
                                networkCallback.onError(response.error_code , response.reson);
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
