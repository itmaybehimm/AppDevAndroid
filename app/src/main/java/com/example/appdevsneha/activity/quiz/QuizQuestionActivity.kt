package com.example.appdevsneha.activity.quiz

import CustomDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appdevsneha.R
import com.example.appdevsneha.data.model.Answer
import com.example.appdevsneha.data.model.Question
import com.example.appdevsneha.data.model.Quiz
import com.example.appdevsneha.data.repository.AnswerViewModel
import com.example.appdevsneha.data.repository.QuestionViewModel
import com.example.appdevsneha.data.repository.QuizViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizQuestionActivity : AppCompatActivity() {
    private lateinit  var quizViewModel: QuizViewModel
    private lateinit  var questionViewModel: QuestionViewModel
    private lateinit  var answerViewModel:AnswerViewModel
    private  var quizId:Int = -1
    private var score=0
    private val maxQuestions = 5
    private var pageNo:Int=1
    private lateinit var questions:LiveData<List<Question>>
    private lateinit var optionAButton:Button
    private lateinit var optionBButton:Button
    private lateinit var optionCButton:Button
    private lateinit var optionDButton:Button
    private lateinit var questionView:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz_question)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        questionViewModel= ViewModelProvider(this)[QuestionViewModel::class.java]
        quizViewModel= ViewModelProvider(this)[QuizViewModel::class.java]
        answerViewModel= ViewModelProvider(this)[AnswerViewModel::class.java]
        questions=questionViewModel.getNQuestions(maxQuestions)

        optionAButton=findViewById(R.id.optionA)
        optionBButton=findViewById(R.id.optionB)
        optionCButton=findViewById(R.id.optionC)
        optionDButton=findViewById(R.id.optionD)
        questionView=findViewById(R.id.question)

        insertQuizAndAnswer()
        displayQuestion()
        optionAButton.setOnClickListener { handleAnswer(1) }
        optionBButton.setOnClickListener { handleAnswer(2) }
        optionCButton.setOnClickListener { handleAnswer(3) }
        optionDButton.setOnClickListener { handleAnswer(4) }

    }

    private fun insertQuizAndAnswer(){
        CoroutineScope(Dispatchers.IO).launch{
            quizId = quizViewModel.addQuizAndReturnId(Quiz()).toInt()

            withContext(Dispatchers.Main) {
                questions.observe(this@QuizQuestionActivity, Observer { questions ->
                    if (questions != null && questions.isNotEmpty()) {
                        CoroutineScope(Dispatchers.IO).launch {
                            questions.forEach { question ->
                                val answer = Answer(
                                    quiz = quizId.toInt(),
                                    questionId = question.id,
                                    answer = null
                                )
                                answerViewModel.addAnswer(answer)
                            }
                        }
                    }
                })
            }
        }
    }

    private fun handleAnswer(selectedOption: Int) {
        questions.value?.let { questions ->
            if (pageNo <= questions.size) {
                val currentQuestion = questions[pageNo - 1]

                if(currentQuestion.answer == selectedOption){
                    score+=1
                    showCorrectPopup()
                    quizViewModel.updateQuiz(Quiz(
                        id = quizId,
                        score = score
                    ))
                }
                else{
                    showIncorrectPopup()
                }

                CoroutineScope(Dispatchers.IO).launch {
                    val existingAnswer = answerViewModel.getAnswerByQuizAndQuestion(quizId.toInt(), currentQuestion.id)
                    existingAnswer?.let {
                        val updatedAnswer = it.copy(answer = selectedOption)
                        answerViewModel.updateAnswer(updatedAnswer)
                    }

                    withContext(Dispatchers.Main) {
                        if (pageNo < maxQuestions) {
                            pageNo++
                            displayQuestion()
                        } else {
                            showQuizCompletePopup()
                        }
                    }
                }
            }
        }
    }


    private fun displayQuestion() {
        questions.observe(this, Observer { questions->
            questionView.text = buildString {
                append("$pageNo. ")
                append(questions[pageNo-1].question)
            }
            optionAButton.text = questions[pageNo-1].optionA
            optionBButton.text = questions[pageNo-1].optionB
            optionCButton.text = questions[pageNo-1].optionC
            optionDButton.text = questions[pageNo-1].optionD
        })
    }

    private fun showCorrectPopup() {
        val dialog = CustomDialog(this, R.layout.popup_correct)
        dialog.show()
        dialog.setCancelable(true)
    }

    private fun showIncorrectPopup() {
        val dialog = CustomDialog(this, R.layout.popup_incorrect)
        dialog.show()
        dialog.setCancelable(true)
    }

    private fun showQuizCompletePopup() {
        val dialog = CustomDialog(this, R.layout.popup_quiz_complete)
        val scoreTextView = dialog.findViewById<TextView>(R.id.scoreText)
        val closeButton = dialog.findViewById<Button>(R.id.closeButton)

        scoreTextView.text = "Your score: $score"

        closeButton.setOnClickListener {
            dialog.dismiss()
            navigateToQuizMainActivity()
        }

        dialog.show()
        dialog.setCancelable(false) // Prevent dismissing on outside touch
    }


    private fun navigateToQuizMainActivity() {
        startActivity(Intent(this, QuizMainActivity::class.java))
        finishAffinity() // Prevents going back to this activity using back button
    }

}