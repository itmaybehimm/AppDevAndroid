package com.example.appdevsneha.activity.quiz

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdevsneha.R
import com.example.appdevsneha.Utils
import com.example.appdevsneha.data.model.Quiz
import com.example.appdevsneha.data.model.User
import com.example.appdevsneha.data.repository.QuizViewModel
import com.example.appdevsneha.databinding.ActivityQuizPreviousBinding

class QuizPreviousActivity : AppCompatActivity() {
    private lateinit var quizzes:LiveData<List<Quiz>>
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var  binding: ActivityQuizPreviousBinding
    private var user: User?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizPreviousBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        user = Utils.getUser(this)
        quizViewModel= ViewModelProvider(this)[QuizViewModel::class.java]
        quizzes=quizViewModel.readAllQuizes(user!!.id)

        populateQuizzes()
    }

    private fun populateQuizzes(){
        quizzes.observe(this) { quizzes ->
            if (quizzes != null) {
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = QuizPreviousAdapter(quizzes) { quiz ->
                        quizViewModel.deleteQuiz(quiz)
                    }
                }
            }
        }
    }
}