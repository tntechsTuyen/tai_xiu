package com.mnt.tx.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.mnt.tx.R;

@SuppressLint("NewApi")
public class IconView extends AppCompatTextView {
    public IconView(Context context) {
        super(context);
        init();
    }

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
//        Typeface typeface = Typeface.createFromAsset( getContext().getAssets(), "fonts/fa-solid-900.ttf" );
        Typeface typeface = getResources().getFont(R.font.fa_solid);
        setTypeface(typeface);
    }
}
