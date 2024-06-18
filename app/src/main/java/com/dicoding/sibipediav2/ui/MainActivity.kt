package com.dicoding.sibipediav2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.sibipediav2.R
import com.dicoding.sibipediav2.databinding.ActivityMainBinding
import com.dicoding.sibipediav2.ui.kamus.KamusActivity
import com.dicoding.sibipediav2.ui.profile.ProfileActivity
import com.dicoding.sibipediav2.ui.quiz.QuizActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            val displayName = currentUser?.displayName ?: "User"
            binding.welcomeText.text = getString(R.string.hello, displayName)
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
           startActivity(intent)
        }

        binding.dictionaryCard.setOnClickListener {
            val intent = Intent(this@MainActivity, KamusActivity::class.java)
            startActivity(intent)
        }

        binding.quizCard.setOnClickListener {
            val intent = Intent(this@MainActivity, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}
