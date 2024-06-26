package com.example.appdevsneha.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.appdevsneha.data.model.Question

@Dao
interface QuestionDao {

    @Insert
    suspend fun insertQuestion(question: Question)

    @Update
    suspend fun updateQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)
}