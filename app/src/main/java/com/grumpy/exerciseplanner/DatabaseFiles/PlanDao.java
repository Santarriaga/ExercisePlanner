package com.grumpy.exerciseplanner.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlanDao {
    @Insert
    void insert(Plan plan);

    @Query("SELECT * FROM plans")
    List<Plan> getAllPlans();

    @Query("DELETE FROM plans WHERE exerciseId=:id")
    void deleteById(int id);
}
