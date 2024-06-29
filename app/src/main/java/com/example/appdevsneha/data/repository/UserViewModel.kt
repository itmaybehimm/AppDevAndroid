package com.example.appdevsneha.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    protected val scope = CoroutineScope(Dispatchers.Main)
    init {
        val userDao = NoteDatabase.getDatabaseInstance(application).userDao()
        repository = UserRepository(userDao)
    }

    fun insertUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        scope.launch {
            try {
                repository.insertUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error occurred")
            }
        }
    }

    fun getUserByUsernameAndPassword(username: String,password:String): LiveData<User> {
        return repository.getUserByUsernameAndPassword(username,password)
    }
}
