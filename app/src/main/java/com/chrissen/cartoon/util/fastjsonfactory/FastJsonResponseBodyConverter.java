package com.chrissen.cartoon.util.fastjsonfactory;

/**
 * Created by guhaibo on 2017/8/31.
 */

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /*
    * 转换方法
    */
    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        if (type == String.class) {
            return (T) tempStr;
        }
        return JSON.parseObject(tempStr, type);
    }
}
