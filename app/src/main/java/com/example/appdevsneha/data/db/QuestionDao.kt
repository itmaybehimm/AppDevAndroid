package com.example.appdevsneha.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appdevsneha.data.model.Question

@Dao
interface QuestionDao {

    @Insert
    suspend fun insertQuestion(question: Question)

    @Update
    suspend fun updateQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(questions: List<Question>)

    @Query("SELECT * FROM question")
    fun getAllQuestions(): LiveData<List<Question>>
    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("SELECT * FROM question ORDER BY RANDOM() LIMIT :numQuestion")
    fun getNQuestions(numQuestion:Int):LiveData<List<Question>>
}