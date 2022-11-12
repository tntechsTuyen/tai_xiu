package com.mnt.tx.widget;

import android.annotation.SuppressLint;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("NewApi")
public class SicboLayout extends LinearLayout {

    Paint paintRect = new Paint(), paintText = new Paint(), paintLine = new Paint();
    int w = 50, h = 50;
    int rows = 6, columns = 0;
    int size = 50;
    Float fontSize = 30f;

    /**
     * data[column][row]
     * */
    private Point current;
    List<List<Point>> data = new ArrayList<>();
    Map<String, Map<Integer, List<Point>>> lines = new LinkedHashMap<>();

    public Point getCurrent() {
        return current;
    }

    public List<List<Point>> getData() {
        return data;
    }

    public Map<String, Map<Integer, List<Point>>> getLines() {
        return lines;
    }

    public void setData(List<List<Point>> data, Map<String, Map<Integer, List<Point>>> lines, Point current){
        this.data = data;
        this.lines = lines;
        this.current = current;
        invalidate();
    }

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

    private void addColumn(List<Point> col){
        this.data.add(col);
        setLayoutParams(new LayoutParams(this.data.size() * size, (rows * size) + 5));
        invalidate();
    }

    /**
     * @param point : {col, row, val}
     * */
    public void addValueItem(Point point){
        List<Point> dataTemp = Arrays.asList(null, null, null, null, null);
        int nCol = 0, nRow = 0;
        if(current != null) {
            if (!this.current.equals(point)) {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).get(0) == null) {
                        nCol = i;
                        break;
                    }
                }
                nRow = 0;
            } else {
                if (this.current.getRow() == rows - 1 || (this.current.getRow() < rows - 1 && data.get(current.getColumn()).get(current.getRow()+1) != null)) {
                    nCol = this.current.getColumn() + 1;
                    nRow = this.current.getRow();
                } else {
                    nCol = this.current.getColumn();
                    nRow = this.current.getRow() + 1;
                }
            }
        }
//        point = new Point(nCol, nRow, point.getData());
        if(this.data.size() - 1 < nCol){
            dataTemp.set(nRow, point);
            addColumn(dataTemp);
        }else{
            this.data.get(nCol).set(nRow, point);
        }
        this.current = (nRow == 0) ?  new Point(nCol, nRow, point.getData(), null) : new Point(nCol, nRow, point.getData(), this.current);

        //Create data line
        Point root = this.current.getNodeRoot();
        String titleFlag = this.current.getNodeTitle();
        if(lines.get(titleFlag) == null){
            Map<Integer, List<Point>> tmpMap = new LinkedHashMap<>();
            tmpMap.put(root.getColumn(), new ArrayList<>());
            lines.put(titleFlag, tmpMap);
        }else if(lines.get(titleFlag).get(root.getColumn()) == null) lines.get(titleFlag).put(root.getColumn(), new ArrayList<>());
        lines.get(titleFlag).get(root.getColumn()).add(this.current);
        invalidate();
    }

    private void init(){
        paintRect.setColor(Color.rgb(0,0,0));
        paintRect.setStyle(Paint.Style.STROKE);

        paintText.setColor(Color.BLACK);
        paintText.setTextSize(fontSize);

        paintLine.setColor(Color.rgb(255, 0, 0));
        paintLine.setStrokeWidth(3f);
    }

    public void initData(){
        System.out.println("SIZE: "+Resources.getSystem().getDisplayMetrics().heightPixels);
        for(int i = 0; i <= Resources.getSystem().getDisplayMetrics().heightPixels / size; i++){
            addColumn(Arrays.asList(null, null, null, null, null, null));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < data.size(); i++){
            paint(canvas, i);
        }
        paintLine(canvas);
    }

    public void paint(Canvas canvas, int col){
        for(int i = 0; i < data.get(col).size(); i++){
            String txt = (data.get(col).get(i) == null) ? "" : data.get(col).get(i).getTitle();
            canvas.drawRect(col * size + 1, (i*size) + 1, col * size + w, (i*size) + h, paintRect);
            canvas.drawText(txt, (float) (col * size + size/2 - fontSize.intValue()/1.5), (float) ((i * size) + size/2 + fontSize.intValue()/1.5), paintText);
        }
    }

    public boolean paintLine(Canvas canvas){
        if(lines.keySet().size() == 0) return false;

        for(String k : lines.keySet()){
            lines.get(k).keySet();
            if(lines.get(k).keySet().size() == 0) continue;
            for(Integer col : lines.get(k).keySet()){
                List<Point> lstPoint = lines.get(k).get(col);
                Point pStart = lstPoint.get(0);
                Point pEnd = lstPoint.get(lstPoint.size()-1);
                if(pStart.getColumn().equals(pEnd.getColumn())) continue;

                for(int i = 1; i < lstPoint.size(); i++){
                    //startX, startY, endX, endY
                    canvas.drawLine((lstPoint.get(i-1).getColumn()) * size + size/2
                            , (lstPoint.get(i-1).getRow()) * size + size/2
                            , (lstPoint.get(i).getColumn()) * size + size/2
                            , (lstPoint.get(i).getRow()) * size + size/2, paintLine);
                }
            }
        }
        return true;
    }
}
