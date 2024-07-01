package com.example.appdevsneha.activity.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.appdevsneha.R
import com.example.appdevsneha.Utils
import com.example.appdevsneha.data.model.User
import com.example.appdevsneha.data.repository.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileUpdateActivity : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var password1Input: EditText
    private lateinit var fullNameInput: EditText
    private lateinit var logoutButton: Button
    private lateinit var deleteAccountButton: Button
    private lateinit var saveButton: Button
    private lateinit var userViewModel: UserViewModel
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        password1Input = findViewById(R.id.password1Input)
        fullNameInput = findViewById(R.id.fullNameInput)
        logoutButton = findViewById(R.id.logoutButton)
        deleteAccountButton = findViewById(R.id.deleteAccountButton)
        saveButton = findViewById(R.id.saveButton)

        userViewModel=ViewModelProvider(this)[UserViewModel::class.java]

        user = Utils.getUser(this)

        loadUserData()
        addListeners()
    }

    private fun loadUserData(){
        if(user!=null){
            usernameInput.hint = user?.username
            fullNameInput.hint = user?.fullName
            passwordInput.hint = "******"
            password1Input.hint="******"
        }
    }

    private fun addListeners() {
        saveButton.setOnClickListener {
            saveUserData()
        }

        logoutButton.setOnClickListener {
            Utils.logoutUser(this)
        }

        deleteAccountButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun saveUserData() {
        val newUsername = usernameInput.text.toString().trim()
        val newPassword = passwordInput.text.toString()
        val confirmPassword = password1Input.text.toString()
        val newFullName = fullNameInput.text.toString().trim()

        if (newUsername.isEmpty() || newFullName.isEmpty()) {
            Toast.makeText(this, "Username and Full Name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword.isNotEmpty() && newPassword.length < 8) {
            Toast.makeText(this, "Passwords must be at least 8 characters long", Toast.LENGTH_SHORT).show()
            return
        }
        if (newPassword.isNotEmpty() && newPassword != confirmPassword) {
            Toast.makeText(this, "Passwords must match", Toast.LENGTH_SHORT).show()
            return
        }

        user?.let { existingUser ->
            val updatedUser = existingUser.copy(
                id = existingUser.id,
                username = newUsername,
                fullName = newFullName,
                password = if (newPassword.isNotEmpty()) newPassword else existingUser.password
            )

            userViewModel.updateUser(updatedUser, {
                Toast.makeText(this@ProfileUpdateActivity, "User updated successfully", Toast.LENGTH_SHORT).show()
                Utils.logoutUser(this)
            }, { errorMessage ->
                if (errorMessage.contains("UNIQUE constraint failed")) {
                    Toast.makeText(this@ProfileUpdateActivity, "Username already exists", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ProfileUpdateActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showDeleteConfirmationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account?")
            .setPositiveButton("Delete") { _, _ ->
                user?.let {
                    userViewModel.deleteUser(it)
                    Utils.logoutUser(this)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}