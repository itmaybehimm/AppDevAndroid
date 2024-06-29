package com.example.appdevsneha.data.repository

import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.QuizDao
import com.example.appdevsneha.data.model.Quiz

class QuizRepository(private val quizDao: QuizDao) {
    fun readAllQuizzes(userId:Int):LiveData<List<Quiz>>{
        return quizDao.readAllQuizes(userId)
    }
    suspend fun addQuiz(quiz: Quiz){
        quizDao.insertQuiz(quiz)
    }

    suspend fun updateQuiz(quiz: Quiz) {
        quizDao.updateQuiz(quiz)
    }

    suspend fun deleteQuiz(quiz: Quiz) {
        quizDao.deleteQuiz(quiz)
    }

    suspend fun addQuizAndReturnId(quiz: Quiz): Long {
        return quizDao.insertQuizAndReturnId(quiz)
    }
}