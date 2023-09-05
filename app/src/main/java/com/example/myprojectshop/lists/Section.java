package com.example.myprojectshop.lists;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "sections23")
public class Section {
    @PrimaryKey(autoGenerate = true)
    private int dbId;
    private String id;
    private String title;
    private String image;
    private String dellImage;

    public Section(int dbId, String id, String title, String image, String dellImage) {
        this.dbId = dbId;
        this.id = id;
        this.title = title;
        this.image = image;
        this.dellImage = dellImage;
    }
@Ignore
    public Section(String id, String title, String image, String dellImage) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.dellImage = dellImage;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDellImage() {
        return dellImage;
    }

    public void setDellImage(String dellImage) {
        this.dellImage = dellImage;
    }
}

