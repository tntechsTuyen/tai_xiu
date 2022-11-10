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

    public Point(Data data){
        this.data = data;
    }

    public Point(Integer column, Integer row, Data data) {
        this.column = column;
        this.row = row;
        this.data = data;
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

    public boolean isRowEnd(){
        return (this.getRow() - 5 == 0);
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
        return this.getTitle().equals(p.getTitle()) || p.getTitle().equals("B");
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
