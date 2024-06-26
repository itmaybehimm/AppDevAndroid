package com.example.appdevsneha.data.repository

import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.QuizDao
import com.example.appdevsneha.data.model.Quiz

class QuizRepository(private val quizDao: QuizDao) {
    val readAllQuizes: LiveData<List<Quiz>> = quizDao.readAllQuizes()
    suspend fun addQuiz(quiz: Quiz){
        quizDao.insertQuiz(quiz)
    }

    suspend fun updateQuiz(quiz: Quiz) {
        quizDao.updateQuiz(quiz)
    }

    suspend fun deleteQuiz(quiz: Quiz) {
        quizDao.deleteQuiz(quiz)
    }


}