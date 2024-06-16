package com.dicoding.sibipediav2.ui.kamus

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.sibipediav2.databinding.ActivityKamusBinding
import com.dicoding.sibipediav2.ui.MainActivity

class KamusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKamusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKamusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alphabetCard.setOnClickListener {
            val intent = Intent(this@KamusActivity,
                AlphabetActivity::class.java)
            startActivity(intent)
        }

        binding.vocabularyCard.setOnClickListener {
            val intent = Intent(this@KamusActivity,
                VocabularyActivity::class.java)
            startActivity(intent)
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@KamusActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        })
    }
}
