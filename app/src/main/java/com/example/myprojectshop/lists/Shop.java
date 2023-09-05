package com.example.myprojectshop.lists;

public class Shop {
    private int id;
    private  String name;
    private  String title;
    private  String nameImage;
    private  String dellImage;
    private double horizontal;
    private double vertical;

    public Shop(int id, String name, String title, String nameImage, String dellImage, double horizontal, double vertical) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.nameImage = nameImage;
        this.dellImage = dellImage;
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public String getDellImage() {
        return dellImage;
    }

    public void setDellImage(String dellImage) {
        this.dellImage = dellImage;
    }

    public double getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(double horizontal) {
        this.horizontal = horizontal;
    }

    public double getVertical() {
        return vertical;
    }

    public void setVertical(double vertical) {
        this.vertical = vertical;
    }
}
