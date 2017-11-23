package com.chrissen.cartoon.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import com.chrissen.cartoon.CartoonApplication;

import java.text.SimpleDateFormat;

/**
 * Created by chris on 2017/11/22.
 */

public class SystemUtil {


    public static boolean isAutoBrightness() {
        boolean automicBrightness = false;
        try{
            automicBrightness = Settings.System.getInt(CartoonApplication.getContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) ==Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch(Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automicBrightness;
    }

    public static void setBrightness(Activity activity, int brightness) {
        // Settings.System.putInt(activity.getContentResolver(),
        // Settings.System.SCREEN_BRIGHTNESS_MODE,
        // Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }


    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,  Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    public static int getActivityBrightness(Activity activity) {
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        int brightness = (int) (params.screenBrightness * 255f);
        return brightness;
    }


    /**
     * 获取屏幕的亮度
     *
     * @param activity
     * @return
     */
    public static int getScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    /**
     * 保存亮度设置状态
     *
     * @param resolver
     * @param brightness
     */
    public static void saveBrightness(ContentResolver resolver, int brightness) {
        Uri uri = android.provider.Settings.System
                .getUriFor("screen_brightness");
        android.provider.Settings.System.putInt(resolver, "screen_brightness",
                brightness);
        resolver.notifyChange(uri, null);
    }


    /**
     * 时间戳转换成date
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = sdf.format(timeStamp);

        return date;

    }


}
