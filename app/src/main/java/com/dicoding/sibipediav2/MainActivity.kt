package com.dicoding.sibipediav2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.sibipediav2.databinding.ActivityMainBinding
import com.dicoding.sibipediav2.ui.kamus.KamusActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dictionaryCard.setOnClickListener { v ->
            val intent =
                Intent(
                    this@MainActivity,
                    KamusActivity::class.java
                )
            startActivity(intent)
        }

        binding.quizCard.setOnClickListener { v ->
//            val intent =
//                Intent(
//                    this@MainActivity,
//                    KamusActivity::class.java
//                )
//            startActivity(intent)
        }
    }
}