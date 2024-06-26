package com.example.appdevsneha.data.repository

import androidx.lifecycle.LiveData
import com.example.appdevsneha.data.db.UserDao
import com.example.appdevsneha.data.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }
    suspend fun updateUser(user: User): Int {
        return userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    fun getUserByUsername(username: String): LiveData<User> {
        return userDao.getUserByUsername(username)
    }

    fun getUserByUsernameAndPassword(username: String,password:String): LiveData<User> {
        return userDao.getUserByUsernameAndPassword(username,password)
    }
}
