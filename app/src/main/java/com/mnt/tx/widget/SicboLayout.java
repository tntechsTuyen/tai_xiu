package com.mnt.tx.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.mnt.tx.R;
import com.mnt.tx.data.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SicboLayout extends LinearLayout {

    private Point current;
    Paint paintRect = new Paint(), paintText = new Paint();
    int w = 100, h = 100;
    int rows = 6, columns = 0;
    int size = 50;

    /**
     * data[column][row]
     * */
    List<List<String>> data = new ArrayList<>();

    public SicboLayout(Context context) {
        super(context);
        init();
    }

    public SicboLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setValueAttr(context, attrs);
        init();
    }

    public SicboLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setValueAttr(context, attrs);
        init();
    }

    public SicboLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setValueAttr(context, attrs);
        init();
    }

    private void setValueAttr(Context mContext, AttributeSet mAttr){
        TypedArray type = mContext.getTheme().obtainStyledAttributes(mAttr, R.styleable.SicboLayout, 0,0);
        this.columns = type.getInteger(R.styleable.SicboLayout_columns, this.columns);
        this.rows = type.getInteger(R.styleable.SicboLayout_rows, 6);
    }

    private void addColumn(List<String> col){
        this.data.add(col);
        setLayoutParams(new LayoutParams(this.data.size() * size, (rows * size) + (size*2)));
        invalidate();
    }

    /**
     * @param point : {col, row, val}
     * */
    public void addValueItem(Point point){
        List<String> dataTemp = Arrays.asList("", "", "", "", "", "");
        int nCol, nRow;
        if(this.current == null){
            nCol = 0; nRow = 0;
        }else{
            if(!this.current.equals(point)){
                nCol = this.current.getColumn()+1; nRow = 0;
            }else{
                if(this.current.isRowEnd()) {
                    nCol = this.current.getColumn() + 1; nRow = this.current.getRow();
                }else{
                    nCol = this.current.getColumn(); nRow = this.current.getRow() + 1;
                }
            }
        }
//        point = new Point(nCol, nRow, point.getData());
        if(this.data.size() - 1 < nCol){
            dataTemp.set(nRow, point.getTitle());
            addColumn(dataTemp);
        }else{
            this.data.get(nCol).set(nRow, point.getTitle());
        }
        this.current = new Point(nCol, nRow, point.getData());
        invalidate();
    }

    private void init(){
        paintRect.setColor(Color.rgb(0,0,0));
        paintRect.setStyle(Paint.Style.STROKE);

        paintText.setColor(Color.BLACK);
        paintText.setTextSize(30f);

    }

    public void initData(){
        for(int i = 0; i <= Resources.getSystem().getDisplayMetrics().widthPixels / size; i++){
            addColumn(Arrays.asList("", "", "", "", "", ""));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < data.size(); i++){
            paint(canvas, i);
        }
    }

    public void paint(Canvas canvas, int col){
        for(int i = 0; i < data.get(col).size(); i++){
            canvas.drawRect(col * size + 1, (i*size) + 1, col * size + w, (i*size) + h, paintRect);
            canvas.drawText(data.get(col).get(i), col * size + w/2, (i * size) + h/2, paintText);
        }
    }
}
