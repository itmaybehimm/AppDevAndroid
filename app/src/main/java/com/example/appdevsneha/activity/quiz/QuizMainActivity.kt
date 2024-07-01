package com.example.appdevsneha.activity.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appdevsneha.PrepopulateData
import com.example.appdevsneha.R
import com.example.appdevsneha.data.repository.QuestionViewModel
import com.example.appdevsneha.data.repository.QuizViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizMainActivity : AppCompatActivity() {
    private lateinit var startQuizButton:Button
    private lateinit var viewQuizButton:Button
    private lateinit var questionViewModel:QuestionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startQuizButton = findViewById(R.id.startQuizButton)
        viewQuizButton=findViewById(R.id.viewQuizButton)

        questionViewModel=ViewModelProvider(this)[QuestionViewModel::class.java]
        // Prepopulate database if necessary
        prepopulateDatabaseIfNeeded()
        addListeners()
    }

    private fun prepopulateDatabaseIfNeeded() {
        questionViewModel.getAllQuestions().observe(this, Observer { questions ->
            if (questions.isEmpty()) {
                val animalList = PrepopulateData.animalQuestions
                animalList.forEach { questionViewModel.addQuestion(it) }
            }
        })
    }

    private fun addListeners(){
        startQuizButton.setOnClickListener{
            val intent = Intent(
                this,
                QuizQuestionActivity::class.java
            )
            startActivity(intent)
        }

        viewQuizButton.setOnClickListener{
            val intent = Intent(
                this,
                QuizPreviousActivity::class.java
            )
            startActivity(intent)
        }
    }

}