package com.mnt.tx.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SicboLayout extends View {

    Paint paint = new Paint();

    public SicboLayout(Context context) {
        super(context);
        init();
    }

    public SicboLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SicboLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SicboLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        paint.setColor(Color.rgb(0,0,0));
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("Draw view");
        for(int i = 1; i <= 6; i++){
            canvas.drawRect(1, 100 + i * 100, 100, 100, paint);
            canvas.drawText(i+"", 50, 50 + (i * 100), paint);
        }
    }
}
