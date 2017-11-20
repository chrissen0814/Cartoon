package com.chrissen.cartoon.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chrissen.cartoon.CartoonApplication;

/**
 * Created by chris on 2017/11/20.
 */

public class PreferenceHelper {

    public static final String IMAGE_COLOR = "image_color";

    public static void putInt( String name , int flag){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(CartoonApplication.getContext()).edit();
        editor.putInt(name,flag);
        editor.apply();
    }

    public static int getInt(String name , int strData){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(CartoonApplication.getContext());
        return sp.getInt(name,strData);
    }

}
