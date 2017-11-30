package com.chrissen.cartoon.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chrissen.cartoon.R;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;

/**
 * Created by chris on 2017/11/17.
 */

public class ImageUtil {

    private static RequestOptions mOptions = new RequestOptions()
            .centerCrop()
            .transform(new MultiTransformation<Bitmap>(new BlurTransformation(15,3),new BrightnessFilterTransformation(-0.3f)))
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    private static RequestOptions sCircleOptions = new RequestOptions()
            .circleCrop();

    private static RequestOptions sPlaceholderOptions = new RequestOptions()
            .centerInside()
            .placeholder(R.drawable.placeholder_cat);


    public static void loadImageByUrl(String url , Context context , ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }

    public static void loadImageByUrlWithPlaceholder(String url , Context context , ImageView imageView){
        Glide.with(context).load(url).apply(sPlaceholderOptions).into(imageView);
    }


    public static void loadBlurImageByRes(int resId , Context context , ImageView imageView){
        Glide.with(context).load(resId)
                .apply(mOptions)
                .into(imageView);
    }

    public static void loadBlurImageByFile(File file , Context context , ImageView imageView){
        Glide.with(context).load(file)
                .apply(mOptions)
                .into(imageView);
    }

    public static void loadCircleImageByRes(int resId , Context context , ImageView imageView){
        Glide.with(context).load(resId).apply(sCircleOptions).into(imageView);
    }

    public static int getIntFromColor(int Red, int Green, int Blue){
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


}
