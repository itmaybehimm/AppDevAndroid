package com.example.appdevsneha.activity.quiz

import QuizPreviousAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdevsneha.R
import com.example.appdevsneha.data.model.Quiz
import com.example.appdevsneha.data.repository.QuizViewModel
import com.example.appdevsneha.databinding.ActivityMainBinding
import com.example.appdevsneha.databinding.ActivityQuizPreviousBinding

class QuizPreviousActivity : AppCompatActivity() {
    private lateinit var quizes:LiveData<List<Quiz>>
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var  binding: ActivityQuizPreviousBinding
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

        quizViewModel= ViewModelProvider(this)[QuizViewModel::class.java]
        quizes=quizViewModel.readAllQuizes()

        populateQuizes()
    }

    private fun populateQuizes(){
        quizes.observe(this, Observer { quizes ->
            if (quizes != null) {
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = QuizPreviousAdapter(quizes,{ quiz ->
                        quizViewModel.deleteQuiz(quiz)
                    })
                }
            }
        })
    }
}