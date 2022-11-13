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

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    public Data getData() {
        return data;
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

    public String getTitleTx() {
        if(data.isSame()){
            title = "B";
        }else{
            if(data.getTotal() >= 4 && data.getTotal() <= 10) title = "X";
            else title = "T";
        }
        return title;
    }

    public boolean equalsTx(Point p){
        return this.getTitleTx().equals(p.getTitleTx()) || p.getTitleTx().equals("B") || equalsChildTx(this, p.getTitleTx());
    }

    public String getTitleLh(){
        if(data.getTotal().equals(11)) return "11";
        else if(data.getTotal() < 11) return "L";
        else if(data.getTotal() > 11) return "H";
        return null;
    }

    public boolean equalsChildTx(Point p, String title){
        if(p.getTitleTx().equals("B")){
            if(p.getParent() != null) return equalsChildTx(p.getParent(), title);
            else return true;
        }else{
            return p.getTitleTx().equals(title);
        }
    }

    public String getNodeTitleTx(){
        if(this.getParent() == null){
            return this.getTitleTx();
        } else {
            if(this.getTitleTx().equals("B")){
                return this.getParent().getNodeTitleTx();
            }else{
                return this.getTitleTx();
            }
        }
    }

    public Point getNodeRoot(){
        return (this.getParent() == null) ? this : this.getParent().getNodeRoot();
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
