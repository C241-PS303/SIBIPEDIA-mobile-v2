package com.dicoding.sibipediav2.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dicoding.sibipediav2.databinding.ActivityProfileBinding
import com.dicoding.sibipediav2.ui.auth.LoginActivity
import com.dicoding.sibipediav2.viewmodel.profile.ProfileViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogout.setOnClickListener {
            profileViewModel.logout()
        }

        profileViewModel.fetchProfile()
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

        profileViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
