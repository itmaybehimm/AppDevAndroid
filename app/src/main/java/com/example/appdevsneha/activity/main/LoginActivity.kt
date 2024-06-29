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
import com.example.appdevsneha.MyApp
import com.example.appdevsneha.R
import com.example.appdevsneha.data.db.NoteDatabase
import com.example.appdevsneha.data.repository.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var signupButton: Button
    private lateinit var usernameInputEditText: TextInputEditText
    private lateinit var passwordInputEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var userViewModel: UserViewModel
    private lateinit var db:NoteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = NoteDatabase.getDatabaseInstance(this)
        usernameInputEditText = findViewById(R.id.usernameInput)
        passwordInputEditText = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        signupButton = findViewById(R.id.signupButton)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        signupButton.setOnClickListener { navigateToSignup() }


        loginButton.setOnClickListener {
            val username = usernameInputEditText.text.toString()
            val password = passwordInputEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
            val username = usernameInputEditText.text.toString()
            val password = passwordInputEditText.text.toString()

            userViewModel.getUserByUsernameAndPassword(username, password).observe(this) { user ->
                if (user != null) {
                    (application as MyApp).currentUser = user
                    (application as MyApp).saveUserToPreferences(user)

                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToSignup(){
        val intent = Intent(this,SignupActivity::class.java)
        startActivity(intent)
    }
}