package com.dicoding.sibipediav2.ui.kamus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.sibipediav2.databinding.ActivityKamusBinding


class KamusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKamusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKamusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alphabetCard.setOnClickListener { v ->
            val intent =
                Intent(
                    this@KamusActivity,
                    AlphabetActivity::class.java
                )
            startActivity(intent)
        }

        binding.vocabularyCard.setOnClickListener { v ->
            val intent =
                Intent(
                    this@KamusActivity,
                    VocabularyActivity::class.java
                )
            startActivity(intent)
        }

    }
}