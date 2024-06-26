package com.example.appdevsneha.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.model.Answer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnswerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AnswerRepository
    protected val scope = CoroutineScope(Dispatchers.Main)

    init {
        val answerDao = NoteDatabase.getDatabaseInstance(application).answerDao()
        repository = AnswerRepository(answerDao)
    }

    fun addAnswer(answer: Answer) {
        scope.launch {
            repository.addAnswer(answer)
        }
    }

    fun updateAnswer(answer: Answer) {
        scope.launch {
            repository.updateAnswer(answer)
        }
    }

    fun deleteAnswer(answer: Answer) {
        scope.launch {
            repository.deleteAnswer(answer)
        }
    }
}
