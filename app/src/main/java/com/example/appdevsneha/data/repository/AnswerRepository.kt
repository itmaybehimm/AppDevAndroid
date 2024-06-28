package com.example.appdevsneha.data.repository

import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.AnswerDao
import com.example.appdevsneha.data.model.Answer

class AnswerRepository(private val answerDao: AnswerDao) {
    suspend fun addAnswer(answer: Answer) {
        answerDao.insertAnswer(answer)
    }

    suspend fun updateAnswer(answer: Answer) {
        answerDao.updateAnswer(answer)
    }

    suspend fun deleteAnswer(answer: Answer) {
        answerDao.deleteAnswer(answer)
    }

     fun getAnswerByQuizAndQuestion(quizId: Int, questionId: Int): Answer? {
        return answerDao.getAnswerByQuizAndQuestion(quizId, questionId)
    }
}
