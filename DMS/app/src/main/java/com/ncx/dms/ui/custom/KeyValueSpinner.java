package com.ncx.dms.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

import androidx.annotation.Nullable;

public class KeyValueSpinner extends androidx.appcompat.widget.AppCompatSpinner {

    public KeyValueSpinner(Context context) {
        super(context);
    }

    public KeyValueSpinner(Context context, int mode) {
        super(context, mode);
    }

    public KeyValueSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnItemSelectedListener(@Nullable OnItemSelectedListener listener) {
        super.setOnItemSelectedListener(listener);
    }
}
