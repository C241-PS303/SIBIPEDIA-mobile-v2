package com.dicoding.sibipediav2.ui.kamus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.sibipediav2.databinding.ActivityGestureDetailBinding

class GestureDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGestureDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        // Get the gesture data from the intent
        val gestureImage = intent.getIntExtra("gestureImage", 0)
        val gestureTitle = intent.getStringExtra("gestureTitle") ?: ""

        // Set the gesture data
        binding.gestureImageView.setImageResource(gestureImage)
        binding.gestureTitle.text = gestureTitle

        // Handle practice button click
        binding.practiceButton.setOnClickListener {
            // Handle the practice button action
        }
    }
}