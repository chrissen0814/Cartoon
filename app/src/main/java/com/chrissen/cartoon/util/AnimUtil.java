package com.chrissen.cartoon.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.chrissen.cartoon.R;

/**
 * Created by caddywang on 14-2-12.
 */
public class AnimUtil {

    public static void fadeIn(View v, Context ctx) {
        fadeIn(v, ctx, false);
    }

    public static void fadeIn(View v, Context ctx, boolean isFast) {
        if (isFast) showOrHideViewFast(v, animFadeIn, R.anim.fade_in, true, ctx);
        else showOrHideView(v, animFadeIn, R.anim.fade_in, true, ctx);
    }

    public static void fadeOut(View v, Context ctx) {
        fadeOut(v, ctx, false);
    }

    public static void fadeOut(View v, Context ctx, boolean isFast) {
        if (isFast) showOrHideViewFast(v, animFadeOut, R.anim.fade_out, false, ctx);
        else showOrHideView(v, animFadeOut, R.anim.fade_out, false, ctx);
    }

    public static void rotateRagularArrow(View v, Context ctx) {
        showOrHideView(v, animRotateArrowRegular, R.anim.rotate_by_regular, true, ctx);
    }

    public static void rotateReverseArrow(View v, Context ctx) {
        showOrHideView(v, animRotateArrowReverse, R.anim.rotate_by_reverse, true, ctx);
    }

    public static void scaleIn(View v, Context ctx) {
        showOrHideView(v, animScaleIn, R.anim.scale_in, true, ctx);
    }

    public static void slideInFromBottom(View v, Context ctx) {
        showOrHideView(v, animSlideInBottom, R.anim.slide_in_from_bottom, true, ctx);
    }

    public static void slideOutFromBottom(View v, Context ctx) {
        showOrHideView(v, animSlideOutBottom, R.anim.slide_out_from_bottom, false, ctx);
    }

    public static void slideOutFromBottom(View v, Context ctx, Animation.AnimationListener animationListener) {
        showOrHideView(v, animSlideOutBottom, R.anim.slide_out_from_bottom, false, ctx, animationListener);
    }

    public static void slideInFromUp(View v, Context ctx) {
        showOrHideView(v, animSlideInUp, R.anim.slide_in_from_up, true, ctx);
    }

    public static void slideOutFromUp(View v, Context ctx) {
        showOrHideView(v, animSlideOutUp, R.anim.slide_out_from_up, false, ctx);
    }

    /**
     * jisj 2014/8/26 增加右入右出的动画方法
     *
     * @param v
     * @param ctx
     */
    public static void slideOutFromRight(View v, Context ctx) {
        showOrHideView(v, animSlideOutRight, R.anim.slide_out_from_right, false, ctx, null);
    }

    public static void slideInFromRight(View v, Context ctx) {
        showOrHideView(v, animSlideInRight, R.anim.slide_in_from_right, true, ctx, null);
    }

    /**
     * 缩小淡入
     */
    public static void shrinkFadeIn(View v, Context ctx) {
        showOrHideView(v, animShrinkFadeIn, R.anim.shrink_fade_in, true, ctx, null);
    }

    /**
     * 放大淡出
     */
    public static void magnifyFadeOut(View v, Context ctx) {
        showOrHideView(v, animMagnifyFadeOut, R.anim.magnify_fade_out, false, ctx, null);
    }

    public static void slideFadeInFromBottom(View v, Context ctx) {
        int[] anims = {R.anim.slide_in_from_bottom, R.anim.fade_in};
        showOrHideView(v, anims, true, ctx);
    }

    public static void slideFadeOutFromBottom(View v, Context ctx) {
        int[] anims = {R.anim.slide_out_from_bottom, R.anim.fade_out};
        showOrHideView(v, anims, false, ctx);
    }

    public static void magnifyDigitalFadeOut(View v, Context ctx) {
        showOrHideView(v, animMagnifyDigitalFadeout, R.anim.magnify_point_out, false, ctx, null);
    }

    public static void bounce(View v, Context ctx) {
        Animation anim = AnimationUtils.loadAnimation(ctx, R.anim.bounce);
        v.startAnimation(anim);
        v.setVisibility(View.VISIBLE);
    }

    public static void loadRotateAnim(View v, Context ctx) {
        if (animaRotate == null) {
            animaRotate = AnimationUtils.loadAnimation(ctx, R.anim.rotate);
        }
        v.startAnimation(animaRotate);
    }


    private static void showOrHideView(View v, Animation anim, int animID, boolean show, Context ctx) {
        showOrHideView(v, anim, animID, show, ctx, null);
    }

    private static void showOrHideView(View v, Animation anim, int animID, boolean show, Context ctx, Animation.AnimationListener animationListener) {
        if (anim == null)
            anim = AnimationUtils.loadAnimation(ctx, animID);

        if (v == null)
            return;

        if (animationListener!=null){
            anim.setAnimationListener(animationListener);
        }else {
            anim.setAnimationListener(null);
        }

        v.startAnimation(anim);
        v.setVisibility((show ? View.VISIBLE : View.INVISIBLE));
    }

    private static void showOrHideViewFast(View v, Animation anim, int animID, boolean show, Context ctx) {
        if (anim == null)
            anim = AnimationUtils.loadAnimation(ctx, animID);

        anim.setDuration(100);

        v.startAnimation(anim);
        v.setVisibility((show ? View.VISIBLE : View.INVISIBLE));
    }

    private static void showOrHideView(View v, int[] animIDs, boolean show, Context ctx) {
        if (v == null)
            return;

        AnimationSet set = new AnimationSet(true);
        for (int id : animIDs) {
            Animation anim = AnimationUtils.loadAnimation(ctx, id);
            set.addAnimation(anim);
        }

        v.startAnimation(set);
        v.setVisibility((show ? View.VISIBLE : View.INVISIBLE));
    }

    private static Animation animFadeIn, animFadeOut, animSlideInBottom, animSlideOutBottom, animSlideInUp, animSlideOutUp, animSlideOutRight, animSlideInRight, animScaleIn;
    private static Animation animShrinkFadeIn, animMagnifyFadeOut, animMagnifyDigitalFadeout, animaRotate, animRotateArrowRegular, animRotateArrowReverse;
}
