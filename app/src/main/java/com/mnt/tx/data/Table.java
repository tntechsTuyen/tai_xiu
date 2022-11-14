package com.mnt.tx.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Table {
    private String name;
    private boolean isActive = false;
    List<Point> data = new ArrayList<>();
    List<Point> data1 = new ArrayList<>();

    public Table(String name, boolean isActive){
        this.name = name;
        this.isActive = isActive;
    }

    public Table(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Point> getData() {
        return data;
    }

    public void setData(List<Point> data) {
        this.data = data;
    }

    public List<Point> getData1() {
        return data1;
    }

    public void setData1(List<Point> data) {
        this.data1 = data;
    }
}
