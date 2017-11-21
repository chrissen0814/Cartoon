package com.chrissen.cartoon.util.view;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by chris on 2017/11/21.
 */

public class LimitEditText extends EditText {


    public LimitEditText(Context context) {
        super(context);
        setInputFilter();
    }

    public LimitEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setInputFilter();
    }

    public LimitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setInputFilter();
    }

    private void setInputFilter(){
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ") || source.toString().contentEquals("\n")) {
                    return "";
                }else{
                    return null;
                }
            }
        };
        setFilters(new InputFilter[]{inputFilter});
    }

}
