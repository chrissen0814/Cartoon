package com.chrissen.cartoon.util.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SeekBar;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.util.SystemUtil;

/**
 * Created by chris on 2017/11/22.
 */

public class BrightnessDialog extends DialogFragment {

    private SeekBar mSeekBar;

    public static BrightnessDialog newInstance(){
        BrightnessDialog dialog = new BrightnessDialog();
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_brightness,container,false);
        initViews(view);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        getDialog().getWindow().setWindowAnimations(R.style.BrightnessDialog_Animation);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        int screenBrightness = SystemUtil.getActivityBrightness(getActivity());
        if (screenBrightness == -255) {
            mSeekBar.setProgress(SystemUtil.getScreenBrightness(getActivity()));
        }else {
            mSeekBar.setProgress(SystemUtil.getActivityBrightness(getActivity()));
        }
    }

    private void initViews(View view) {
        mSeekBar = view.findViewById(R.id.brightness_seekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (SystemUtil.isAutoBrightness()) {
                    SystemUtil.stopAutoBrightness(getActivity());
                }
                SystemUtil.setBrightness(getActivity(),progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }




}
