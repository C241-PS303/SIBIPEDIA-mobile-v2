package com.dicoding.sibipediav2.ui.quiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.sibipediav2.R
import com.dicoding.sibipediav2.databinding.ActivityMainBinding
import com.dicoding.sibipediav2.databinding.ActivityQuizBinding
import com.dicoding.sibipediav2.ui.kamus.KamusActivity

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startQuizButton.setOnClickListener {
            val intent = Intent(this@QuizActivity, QuestionActivity::class.java)
            startActivity(intent)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}