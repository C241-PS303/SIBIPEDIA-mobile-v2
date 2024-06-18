package com.dicoding.sibipediav2.ui.quiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dicoding.sibipediav2.R
import com.dicoding.sibipediav2.data.remote.response.QuestionsItem
import com.dicoding.sibipediav2.data.remote.request.Answer
import com.dicoding.sibipediav2.databinding.ActivityQuestionBinding
import com.dicoding.sibipediav2.ui.score.ScoreActivity
import com.dicoding.sibipediav2.viewmodel.QuestionViewModel

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private val viewModel: QuestionViewModel by viewModels()
    private var currentQuestionIndex = 0
    private lateinit var quizQuestions: List<QuestionsItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationDialog()
            }
        })

        viewModel.fetchQuestions()
        viewModel.questions.observe(this, Observer { questions ->
            if (questions != null) {
                quizQuestions = questions
                displayQuestion(currentQuestionIndex)
            }
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        binding.nextButton.setOnClickListener {
            handleNextButton()
        }

        binding.submitButton.setOnClickListener {
            handleSubmitButton()
        }

        viewModel.score.observe(this, Observer { score ->
            if (score != null) {
                val intent = Intent(this, ScoreActivity::class.java).apply {
                    putExtra(ScoreActivity.EXTRA_SCORE, score)
                }
                startActivity(intent)
                finish()
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleNextButton() {
        val selectedOptionId = binding.optionsGroup.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedOption = findViewById<RadioButton>(selectedOptionId)?.text.toString()
            viewModel.saveAnswer(currentQuestionIndex, selectedOption)
            if (currentQuestionIndex < quizQuestions.size - 1) {
                currentQuestionIndex++
                displayQuestion(currentQuestionIndex)
            }
        } else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handlePreviousButton() {
        val selectedOptionId = binding.optionsGroup.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedOption = findViewById<RadioButton>(selectedOptionId)?.text.toString()
            viewModel.saveAnswer(currentQuestionIndex, selectedOption)
        }
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
            displayQuestion(currentQuestionIndex)
        }
    }

    private fun handleSubmitButton() {
        val selectedOptionId = binding.optionsGroup.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedOption = findViewById<RadioButton>(selectedOptionId)?.text.toString()
            viewModel.saveAnswer(currentQuestionIndex, selectedOption)
            val userAnswers = collectUserAnswers()
            viewModel.submitAnswers(userAnswers)
        } else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("Batalkan quiz?")
            .setPositiveButton("Ya") { dialog, which ->
                finish()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun displayQuestion(index: Int) {
        val question = quizQuestions[index]
        binding.questionNumber.text = getString(R.string.question_number, index + 1)
        binding.questionText.text = question.questionText
        Glide.with(this).load(question.imageUrl).into(binding.questionImage)
        binding.optionsGroup.removeAllViews()
        question.options.forEach { option ->
            val radioButton = RadioButton(this).apply {
                text = option
            }
            binding.optionsGroup.addView(radioButton)
        }

        viewModel.answers.value?.get(index)?.let { savedAnswer ->
            for (i in 0 until binding.optionsGroup.childCount) {
                val radioButton = binding.optionsGroup.getChildAt(i) as RadioButton
                if (radioButton.text == savedAnswer) {
                    radioButton.isChecked = true
                    break
                }
            }
        }

        when (index) {
            0 -> {
                binding.nextButton.visibility = View.VISIBLE
                binding.submitButton.visibility = View.GONE
            }
            quizQuestions.size - 1 -> {
                binding.nextButton.visibility = View.GONE
                binding.submitButton.visibility = View.VISIBLE
            }
            else -> {
                binding.nextButton.visibility = View.VISIBLE
                binding.submitButton.visibility = View.GONE
            }
        }
    }

    private fun collectUserAnswers(): List<Answer> {
        return quizQuestions.mapIndexed { index, question ->
            Answer(
                questionId = question.id.toString(),
                userAnswer = viewModel.answers.value?.get(index) ?: ""
            )
        }
    }
}
