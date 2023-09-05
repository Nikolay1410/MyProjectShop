package com.example.myprojectshop.lists;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class PostShop {

    private int id;
    private String name;
    private String title;
    private String nameImage;
    private double horizontal;
    private double vertical;
    public Map<String, Boolean> stars = new HashMap<>();

    public PostShop(int id, String name, String title, String nameImage, double horizontal, double vertical) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.nameImage = nameImage;
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("title", title);
        result.put("nameImage", nameImage);
        result.put("horizontal", horizontal);
        result.put("vertical", vertical);

        return result;
    }
}
