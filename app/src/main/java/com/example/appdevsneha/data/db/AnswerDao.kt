package com.example.appdevsneha.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.appdevsneha.data.model.Answer

@Dao
interface AnswerDao {

    @Upsert
    suspend fun insertAnswer(answer: Answer)

    @Update
    suspend fun updateAnswer(answer: Answer)

    @Delete
    suspend fun deleteAnswer(answer: Answer)

    @Query("SELECT * FROM answer WHERE quiz = :quizId AND question = :questionId LIMIT 1")
    fun getAnswerByQuizAndQuestion(quizId: Int, questionId: Int): Answer?
}