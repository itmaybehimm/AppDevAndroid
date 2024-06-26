package com.example.appdevsneha.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.appdevsneha.data.model.Answer

@Dao
interface AnswerDao {

    @Insert
    suspend fun insertAnswer(answer: Answer)

    @Update
    suspend fun updateAnswer(answer: Answer)

    @Delete
    suspend fun deleteAnswer(answer: Answer)
}