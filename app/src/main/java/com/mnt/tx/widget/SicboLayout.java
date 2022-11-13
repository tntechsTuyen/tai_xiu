package com.mnt.tx.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.mnt.tx.R;
import com.mnt.tx.data.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("NewApi")
public class SicboLayout extends LinearLayout {

    Paint paintRect = new Paint()
            , paintTextBlue = new Paint()
            , paintTextRed = new Paint()
            , paintTextBlack = new Paint()
            , paintLine = new Paint()
            , paintCircle = new Paint();
    Map<String, Paint> mapPaint = new HashMap<>();
    int w = 50, h = 50;
    int rows = 6, columns = 0;
    int size = 50;
    int flag = 1;
    Float fontSize = 25f;

    /**
     * data[column][row]
     * */
    private List<Point> data;
    List<List<Point>> points = new ArrayList<>();
    Map<String, Map<Integer, List<Point>>> lines = new LinkedHashMap<>();

    public List<Point> getData() {
        return data;
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

    public void initData(){
        for(int i = 0; i <= 100; i++){
            addColumn(Arrays.asList(null, null, null, null, null, null));
        }
    }

    private void setValueAttr(Context mContext, AttributeSet mAttr){
        TypedArray type = mContext.getTheme().obtainStyledAttributes(mAttr, R.styleable.SicboLayout, 0,0);
        this.columns = type.getInteger(R.styleable.SicboLayout_columns, this.columns);
        this.rows = type.getInteger(R.styleable.SicboLayout_rows, 6);
        this.flag = type.getInteger(R.styleable.SicboLayout_flag, 0);
    }

    private void clearPoints(){
        for(int i = 0; i < points.size(); i++){
            //Column
            for(int j = 0; j < points.get(i).size(); j++){
                //Row
                points.get(i).set(j, null);
            }
        }
    }

    private void addColumn(List<Point> col){
        this.points.add(col);
        setLayoutParams(new LayoutParams(this.points.size() * size, (rows * size) + 5));
        invalidate();
    }

    /**
     * */
    public void buildData(List<Point> mData){
        this.data = mData;
        List<Point> dataTemp = Arrays.asList(null, null, null, null, null);
        int nCol = 0, nRow = 0;
        clearPoints();
        for(int i = 0; i < data.size(); i++){
            if(i > 0) {
                if (!data.get(i-1).equals(data.get(i), flag)) {
                    for (int j = 0; j < points.size(); j++) {
                        if (points.get(j).get(0) == null) {
                            nCol = j;
                            break;
                        }
                    }
                    nRow = 0;
                } else {
                    if (data.get(i-1).getRow() == rows - 1
                            || (data.get(i-1).getRow() < rows - 1 && points.get(data.get(i-1).getColumn()).get(data.get(i-1).getRow()+1) != null )) {
                        nCol = data.get(i-1).getColumn() + 1;
                        nRow = data.get(i-1).getRow();
                        data.get(i).setUpToDown(false);
                        data.get(i).setLeftToRight(true);
                    } else {
                        if(data.get(i-1).isLeftToRight()){
                            nCol = data.get(i-1).getColumn() + 1;
                            nRow = data.get(i-1).getRow();
                            data.get(i).setUpToDown(false);
                            data.get(i).setLeftToRight(true);
                        }else{
                            nCol = data.get(i-1).getColumn();
                            nRow = data.get(i-1).getRow() + 1;
                        }
                    }
                    data.get(i).setParent(data.get(i-1));
                }
            }
            data.get(i).setColumn(nCol);
            data.get(i).setRow(nRow);
            if(this.points.size() - 1 < nCol){
                dataTemp.set(nRow, data.get(i));
                addColumn(dataTemp);
            }else{
                this.points.get(nCol).set(nRow, data.get(i));
            }
        }

        //Create data line
        lines.clear();
        List<Point> pointNone = new ArrayList<>();
        for(int i = 0; i < data.size(); i++){
            Point root = data.get(i).getNodeRoot();
            String titleFlag = data.get(i).getNodeTitle(flag);
            if(lines.get(titleFlag) == null){
                if(titleFlag.equals("B") || titleFlag.equals("11")){
                    pointNone.add(data.get(i));
                    continue;
                }
                Map<Integer, List<Point>> tmpMap = new LinkedHashMap<>();
                tmpMap.put(root.getColumn(), new ArrayList<>());
                lines.put(titleFlag, tmpMap);
            }else if(lines.get(titleFlag).get(root.getColumn()) == null) lines.get(titleFlag).put(root.getColumn(), new ArrayList<>());

            if(pointNone.size() > 0){
                lines.get(titleFlag).get(root.getColumn()).addAll(pointNone);
                pointNone.clear();
            }
            lines.get(titleFlag).get(root.getColumn()).add(data.get(i));
        }
        invalidate();
    }

    private void init(){
        paintRect.setColor(Color.rgb(0,0,0));
        paintRect.setStyle(Paint.Style.STROKE);

        paintTextBlack.setColor(Color.BLACK);
        paintTextBlack.setTextSize(fontSize);
        paintTextBlack.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paintTextRed.setColor(Color.RED);
        paintTextRed.setTextSize(fontSize);
        paintTextRed.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paintTextBlue.setColor(Color.BLUE);
        paintTextBlue.setTextSize(fontSize);
        paintTextBlue.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        mapPaint.put("T", paintTextRed);
        mapPaint.put("L", paintTextRed);
        mapPaint.put("X", paintTextBlue);
        mapPaint.put("H", paintTextBlue);
        mapPaint.put("B", paintTextBlack);
        mapPaint.put("11", paintTextBlack);

        paintLine.setColor(Color.rgb(255, 0, 0));
        paintLine.setStrokeWidth(3f);

        paintCircle.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintTable(canvas);
        paintLine(canvas);
    }

    public void paintTable(Canvas canvas){
        for(int col = 0; col < points.size(); col++) {
            for (int row = 0; row < points.get(col).size(); row++) {
                String txt = (points.get(col).get(row) == null) ? "" : points.get(col).get(row).getTitle(flag);
                canvas.drawRect(col * size + 1, (row * size) + 1, col * size + w, (row * size) + h, paintRect);

                if (txt != null && txt.length() > 0){
                    if(txt.equals("11")){
                        canvas.drawCircle((float) ((col+0.5) * size), (float) ((row+0.5) * size), size/3, paintCircle);
                    }
                    canvas.drawText(txt, (float) (col * size + size / 2 - fontSize.intValue() / 2), (float) ((row * size) + size / 2 + fontSize.intValue() / 2), mapPaint.get(txt));
                }
            }
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
                System.out.print(String.format("%s [%d]: [%d][%d] ", k, col, col, 0));
                for(int i = 1; i < lstPoint.size(); i++){
                    //startX, startY, endX, endY
                    System.out.print(String.format(" [%d][%d] ", lstPoint.get(i).getColumn(), lstPoint.get(i).getRow()));
                    canvas.drawLine((lstPoint.get(i-1).getColumn()) * size + size/2
                            , (lstPoint.get(i-1).getRow()) * size + size/2
                            , (lstPoint.get(i).getColumn()) * size + size/2
                            , (lstPoint.get(i).getRow()) * size + size/2, paintLine);
                }
                System.out.println("\n");
            }
        }
        return true;
    }

}
