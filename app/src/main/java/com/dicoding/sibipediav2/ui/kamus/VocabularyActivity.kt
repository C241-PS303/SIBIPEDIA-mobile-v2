package com.dicoding.sibipediav2.ui.kamus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.sibipediav2.databinding.ActivityAlphabetBinding
import com.dicoding.sibipediav2.databinding.ActivityVocabularyBinding

class VocabularyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVocabularyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVocabularyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}