package com.example.appdevsneha.data.repository

import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.QuestionDao
import com.example.appdevsneha.data.model.Question

class QuestionRepository(private val questionDao: QuestionDao) {

    suspend fun addQuestion(question: Question) {
        questionDao.insertQuestion(question)
    }

    suspend fun updateQuestion(question: Question) {
        questionDao.updateQuestion(question)
    }

    suspend fun deleteQuestion(question: Question) {
        questionDao.deleteQuestion(question)
    }

    fun getNQuestions(numQuestion:Int):LiveData<List<Question>>{
        return questionDao.getNQuestions(numQuestion)
    }


}
