package com.dicoding.sibipediav2.ui.score

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.sibipediav2.R
import com.dicoding.sibipediav2.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra(EXTRA_SCORE, 0)
        binding.scoreValue.text = getString(R.string.your_score, score)

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_SCORE = "extra_score"
    }
}
