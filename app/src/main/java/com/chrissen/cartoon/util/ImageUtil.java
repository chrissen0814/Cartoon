package com.chrissen.cartoon.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by chris on 2017/11/17.
 */

public class ImageUtil {

    public static void loadImageByUrl(String url , Context context , ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }

}
