package com.example.appdevsneha.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appdevsneha.data.model.Quiz

@Dao
interface QuizDao {
    @Insert
    suspend fun insertQuiz(quiz: Quiz)

    @Update
    suspend fun updateQuiz(quiz: Quiz)

    @Delete
    suspend fun deleteQuiz(quiz: Quiz)

    @Query("SELECT * FROM quiz")
    fun readAllQuizes():LiveData<List<Quiz>>
}