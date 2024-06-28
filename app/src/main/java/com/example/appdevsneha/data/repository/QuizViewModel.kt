package com.example.appdevsneha.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.model.Note
import com.example.appdevsneha.data.model.Quiz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(application: Application): AndroidViewModel(application) {
    private val readAllQuizes: LiveData<List<Quiz>>
    private val repository: QuizRepository
    protected val scope = CoroutineScope(Dispatchers.Main)
    init{
        val quizDao = NoteDatabase.getDatabaseInstance(application).quizDao()
        repository = QuizRepository(quizDao)
        readAllQuizes = repository.readAllQuizes
    }

    fun readAllQuizes(): LiveData<List<Quiz>> {
        return readAllQuizes
    }

    fun addQuiz(quiz: Quiz){
        scope.launch {
            repository.addQuiz(quiz)
        }
    }

    fun updateQuiz(quiz: Quiz) {
        scope.launch {
            repository.updateQuiz(quiz)
        }

    }

    fun deleteQuiz(quiz:Quiz){
        scope.launch {
            repository.deleteQuiz(quiz)
        }
    }

    suspend fun addQuizAndReturnId(quiz: Quiz): Long {
        return repository.addQuizAndReturnId(quiz)
    }

}