package com.mnt.tx.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Table {
    private String name;
    private boolean isActive = false;
    private Point current;
    List<List<Point>> points = new ArrayList<>();
    Map<String, Map<Integer, List<Point>>> lines = new LinkedHashMap<>();

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
}
