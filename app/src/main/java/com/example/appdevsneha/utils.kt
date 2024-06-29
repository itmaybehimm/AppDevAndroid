package com.example.appdevsneha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.appdevsneha.activity.main.LoginActivity
import com.example.appdevsneha.data.model.User

object Utils {
    fun getUser(context: AppCompatActivity): User? {
        val user = (context.applicationContext as MyApp).currentUser
        if (user == null) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            context.finishAffinity()
            return null
        }
        return user
    }
}
