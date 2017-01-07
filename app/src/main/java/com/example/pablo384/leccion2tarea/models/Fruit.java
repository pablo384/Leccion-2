package com.example.pablo384.leccion2tarea.models;

/**
 * Created by pablo384 on 12/11/16.
 */

public class Fruit {
    private String name, origin;
    private int icon;

    public  Fruit(){}

    public Fruit(String name, int icon,String origin){
        this.name=name;
        this.icon=icon;
        this.origin=origin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
