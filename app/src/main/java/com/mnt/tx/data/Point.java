package com.mnt.tx.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Point {
    private Integer column;
    private Integer row;
    private Data data;
    private String title;
    private Point parent, before;
    private boolean isLeftToRight = false, isUpToDown = true;

    public Point(Integer column, Integer row){
        this.column = column;
        this.row = row;
    }

    public Point(Data data){
        this.data = data;
    }

    public Point(Integer column, Integer row, Data data, Point parent, Point before) {
        this.column = column;
        this.row = row;
        this.data = data;
        this.parent = parent;
        this.before = before;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public void setRow(Integer row) {
        this.row = row;
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

    public void setParent(Point parent) {
        this.parent = parent;
    }

    public Point getParent() {
        return parent;
    }

    public Point getBefore() {
        return before;
    }

    public void setBefore(Point before) {
        this.before = before;
    }

    public String getTitle(int flag) {
        if(flag == 1){
            if(data.isSame()){
                return "B";
            }else{
                if(data.getTotal() >= 4 && data.getTotal() <= 10) return "X";
                else return "T";
            }
        }else{
            if(data.getTotal().equals(11)) return "11";
            else if(data.getTotal() < 11) return "L";
            else if(data.getTotal() > 11) return "H";
        }
        return null;
    }

    public boolean equals(Point p, int flag){
        if(flag == 1) return this.getTitle(flag).equals(p.getTitle(flag)) || p.getTitle(flag).equals("B") || equalsChild(this, p.getTitle(flag), flag);
        else return this.getTitle(flag).equals(p.getTitle(flag)) || p.getTitle(flag).equals("11") || equalsChild(this, p.getTitle(flag), flag);
    }

    public boolean equalsChild(Point p, String title, int flag){
        if(p.getTitle(flag).equals("B") || p.getTitle(flag).equals("11")){
            if(p.getParent() != null) return equalsChild(p.getParent(), title, flag);
            else return true;
        }else{
            return p.getTitle(flag).equals(title);
        }
    }

    public String getNodeTitle(int flag){
        if(this.getParent() == null){
            return this.getTitle(flag);
        } else {
            if(this.getTitle(flag).equals("B") || this.getTitle(flag).equals("11")){
                return this.getParent().getNodeTitle(flag);
            }else{
                return this.getTitle(flag);
            }
        }
    }

    public Point getNodeRoot(){
        return (this.getParent() == null) ? this : this.getParent().getNodeRoot();
    }

    public boolean isLeftToRight() {
        return isLeftToRight;
    }

    public void setLeftToRight(boolean leftToRight) {
        isLeftToRight = leftToRight;
    }

    public boolean isUpToDown() {
        return isUpToDown;
    }

    public void setUpToDown(boolean upToDown) {
        isUpToDown = upToDown;
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
