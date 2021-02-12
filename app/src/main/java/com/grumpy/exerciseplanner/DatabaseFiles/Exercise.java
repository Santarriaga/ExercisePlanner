package com.grumpy.exerciseplanner.DatabaseFiles;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Exercises")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private String category;
    private String imgUrl;

    public Exercise(String name, String shortDescription, String longDescription, String category, String imgUrl) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.category = category;
        this.imgUrl = imgUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
