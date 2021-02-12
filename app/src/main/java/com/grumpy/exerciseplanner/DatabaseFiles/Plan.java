package com.grumpy.exerciseplanner.DatabaseFiles;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plans")
public class Plan {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int exerciseId;
    private int minutes;
    private String day;
    private boolean isAccomplished;

    public Plan(int exerciseId, int minutes, String day, boolean isAccomplished) {
        this.exerciseId = exerciseId;
        this.minutes = minutes;
        this.day = day;
        this.isAccomplished = isAccomplished;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isAccomplished() {
        return isAccomplished;
    }

    public void setAccomplished(boolean accomplished) {
        isAccomplished = accomplished;
    }
}
