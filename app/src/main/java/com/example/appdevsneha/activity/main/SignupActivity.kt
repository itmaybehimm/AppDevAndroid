package com.example.appdevsneha.activity.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.appdevsneha.R
import com.example.appdevsneha.activity.quiz.QuizMainActivity
import com.example.appdevsneha.data.model.User
import com.example.appdevsneha.data.repository.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class SignupActivity : AppCompatActivity() {
    private lateinit var usernameInputEditText: TextInputEditText
    private lateinit var passwordInputEditText: TextInputEditText
    private lateinit var password2InputEditText: TextInputEditText
    private lateinit var fullNameInputEditText: TextInputEditText
    private lateinit var signupButton:Button
    private lateinit var loginButton: Button
    private lateinit var userViewModel:UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usernameInputEditText = findViewById(R.id.usernameInput)
        passwordInputEditText = findViewById(R.id.passwordInput)
        password2InputEditText = findViewById(R.id.password2Input)
        fullNameInputEditText = findViewById(R.id.nameInput)
        signupButton = findViewById(R.id.signupButton)
        loginButton = findViewById(R.id.loginButton)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        signupButton.setOnClickListener {
            val fullName = fullNameInputEditText.text.toString()
            val username = usernameInputEditText.text.toString()
            val password = passwordInputEditText.text.toString()
            val password2 = password2InputEditText.text.toString()

            if (validateInput(fullName, username, password, password2)) {
                registerUser(fullName, username, password)
            }
        }
    }

    private fun registerUser(fullName: String, username: String, password: String) {
        userViewModel.insertUser(User(fullName, username, password), {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }, { errorMessage ->
            if (errorMessage.contains("UNIQUE constraint failed")) {
                Toast.makeText(this, "Username Already exists", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validateInput(fullName: String, username: String, password: String, password2: String): Boolean {
        val usernameRegex = "^[a-z0-9]*$".toRegex()
        return when {
            fullName.isEmpty() || username.isEmpty() || password.isEmpty() -> {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                false
            }
            username.length < 4 -> {
                Toast.makeText(this, "Username must be at least 4 characters", Toast.LENGTH_SHORT).show()
                false
            }
            !username.matches(usernameRegex) -> {
                Toast.makeText(this, "Username can only contain lowercase letters and numbers", Toast.LENGTH_SHORT).show()
                false
            }
            password.length < 8 -> {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                false
            }
            password != password2 -> {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}