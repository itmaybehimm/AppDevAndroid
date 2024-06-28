package com.example.appdevsneha.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.model.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: QuestionRepository
    protected val scope = CoroutineScope(Dispatchers.Main)

    init {
        val questionDao = NoteDatabase.getDatabaseInstance(application).questionDao()
        repository = QuestionRepository(questionDao)
    }

    fun addQuestion(question: Question) {
        scope.launch {
            repository.addQuestion(question)
        }
    }

    fun updateQuestion(question: Question) {
        scope.launch {
            repository.updateQuestion(question)
        }
    }

    fun deleteQuestion(question: Question) {
        scope.launch {
            repository.deleteQuestion(question)
        }
    }

    fun getNQuestions(numQuestion:Int):LiveData<List<Question>>{
        return repository.getNQuestions(numQuestion)
    }
}
