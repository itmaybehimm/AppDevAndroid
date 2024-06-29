package com.example.appdevsneha

import android.app.Application
import android.content.Context
import com.example.appdevsneha.data.model.User

class MyApp : Application() {
    var currentUser: User? = null

    override fun onCreate() {
        super.onCreate()
        // Load user from shared preferences if exists
        currentUser = loadUserFromPreferences()
    }

    private fun loadUserFromPreferences(): User? {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", -1)
        val username = sharedPreferences.getString("username", null)
        val fullName = sharedPreferences.getString("fullName", null)
        val password = sharedPreferences.getString("password", null)

        return if (id != -1 && username != null && fullName != null && password != null) {
            User(id=id, username = username, fullName = fullName, password = password)
        } else {
            null
        }
    }

    fun saveUserToPreferences(user: User) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("id", user.id)
            putString("username", user.username)
            putString("fullName",user.fullName)
            putString("password",user.password)
            apply()
        }
    }
}
