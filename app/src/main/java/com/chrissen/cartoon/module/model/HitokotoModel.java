package com.chrissen.cartoon.module.model;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.chrissen.cartoon.bean.HitokotoBean;
import com.chrissen.cartoon.util.ConfigUtil;
import com.chrissen.cartoon.util.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chris on 2017/11/19.
 */

public class HitokotoModel {



    public void getHitokoto(final Handler handler){

        Call call = OkHttpUtil.newInstance().newCall(new Request.Builder().url(ConfigUtil.HITOKOTO_URL).build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                if (response.isSuccessful()) {
                    HitokotoBean hitokotoBean = JSON.parseObject(response.body().string(),HitokotoBean.class);
                    message.what = ConfigUtil.SUCCESS_MSG;
                    message.obj = hitokotoBean;
                    handler.sendMessage(message);
                }else {
                    message.what = ConfigUtil.FAIL_MSG;
                    handler.sendMessage(message);
                }
            }
        });

    }

}
