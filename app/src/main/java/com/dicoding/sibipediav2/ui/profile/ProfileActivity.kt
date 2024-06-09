package com.dicoding.sibipediav2.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.sibipediav2.databinding.ActivityProfileBinding
import com.dicoding.sibipediav2.ui.auth.LoginActivity
import com.dicoding.sibipediav2.viewmodel.profile.ProfileViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val loginViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogout.setOnClickListener {
            loginViewModel.logout()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        loginViewModel.logoutSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}