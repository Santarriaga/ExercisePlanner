package com.grumpy.exerciseplanner.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert
    void insert(Exercise exercise);

    @Query("SELECT * FROM Exercises")
    List<Exercise> getAllExercises();

    @Query("SELECT * FROM Exercises WHERE id=:id")
    Exercise getItemById(int id);

    @Query("SELECT * FROM Exercises WHERE name LIKE:text")
    List<Exercise> searchForExercise(String text);

    @Query("SELECT * FROM Exercises WHERE category = :category")
    List<Exercise> getItemsByCategory(String category);
}
