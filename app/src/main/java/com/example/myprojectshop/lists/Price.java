package com.example.myprojectshop.lists;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "prices23")
public class Price {
    @PrimaryKey(autoGenerate = true)
    private int dbId;
    private String id;
    private String sectionId;
    private String name;
    private String image;
    private double cost;
    private double costSail;
    private String priceUnit;

    public Price(int dbId, String id, String sectionId, String name, String image, double cost, double costSail, String priceUnit) {
        this.dbId = dbId;
        this.id = id;
        this.sectionId = sectionId;
        this.name = name;
        this.image = image;
        this.cost = cost;
        this.costSail = costSail;
        this.priceUnit = priceUnit;
    }
@Ignore
    public Price(String id, String sectionId, String name, String image, double cost, double costSail, String priceUnit) {
        this.id = id;
        this.sectionId = sectionId;
        this.name = name;
        this.image = image;
        this.cost = cost;
        this.costSail = costSail;
        this.priceUnit = priceUnit;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCostSail() {
        return costSail;
    }

    public void setCostSail(double costSail) {
        this.costSail = costSail;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }
}
