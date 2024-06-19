package com.dicoding.sibipediav2.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.sibipediav2.databinding.ActivityProfileBinding
import com.dicoding.sibipediav2.ui.adapter.QuizScoreAdapter
import com.dicoding.sibipediav2.ui.auth.LoginActivity
import com.dicoding.sibipediav2.viewmodel.profile.ProfileViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var adapter: QuizScoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogout.setOnClickListener {
            profileViewModel.logout()
        }

        adapter = QuizScoreAdapter()
        binding.rvHistorySkor.layoutManager = LinearLayoutManager(this)
        binding.rvHistorySkor.adapter = adapter

        profileViewModel.fetchProfile()
        profileViewModel.fetchUserQuizRecords()
        observeViewModel()
    }

    private fun observeViewModel() {
        profileViewModel.logoutSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                navigateToLogin()
            }
        }

        profileViewModel.profile.observe(this) { profile ->
            if (profile != null) {
                binding.tvName.text = profile.displayName
                binding.tvEmail.text = profile.email
                binding.tvPhone.text = profile.phoneNumber
            }
        }

        profileViewModel.userQuizRecords.observe(this) { records ->
            val quizRecords = records.values.toList()
            adapter.setQuizRecords(quizRecords)
        }

        profileViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
