package com.mnt.tx.data;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Point {
    private Integer column;
    private Integer row;
    private Data data;
    private String title;
    private Point child;

    public Point(Data data){
        this.data = data;
    }

    public Point(Integer column, Integer row, String title){
        this.column = column;
        this.row = row;
        this.title = title;
    }

    public Point(Integer column, Integer row, Data data, Point child) {
        this.column = column;
        this.row = row;
        this.data = data;
        this.child = child;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    public Data getData() {
        return data;
    }

    public Point getChild() {
        return child;
    }

    public String getTitle() {
        if(data.isSame()){
            if(data.getTotal() >= 4 && data.getTotal() <= 10) title = "X";
            else title = "T";
        }else{
            title = "B";
        }
        return title;
    }

    public boolean equals(Point p){
        return this.getTitle().equals(p.getTitle()) || p.getTitle().equals("B") || equalsChild(this, p.getTitle());
    }

    public boolean equalsChild(Point p, String title){
        if(p.getTitle().equals("B")){
            if(p.getChild() != null) return equalsChild(p.getChild(), title);
            else return true;
        }else{
            return p.getTitle().equals(title);
        }
    }

    public static class Data{
        private Integer val1, val2, val3;

        public Data(Integer val1, Integer val2, Integer val3) {
            this.val1 = val1;
            this.val2 = val2;
            this.val3 = val3;
        }

        public boolean isSame(){
            List<Integer> tmp = Arrays.asList(this.val1, this.val2, this.val3);
            Set<Integer> unique = new HashSet<>(tmp);
            return unique.size() == 1;
        }

        public Integer getTotal(){
            return this.val1 + this.val2 + this.val3;
        }
    }
}
