package com.dicoding.sibipediav2.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dicoding.sibipediav2.data.remote.request.RegisterRequest
import com.dicoding.sibipediav2.databinding.ActivityRegisterBinding
import com.dicoding.sibipediav2.viewmodel.auth.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        setupObservers()
        setupActions()
    }

    private fun setupActions() {
        binding.createAccountButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val fullName = binding.fullNameEditText.text.toString()
            val phoneNumber = binding.phoneNumberEditText.text.toString()

            if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && fullName.isNotBlank() && phoneNumber.isNotBlank()) {
                val registerRequest = RegisterRequest(username, email, password, fullName, phoneNumber)
                registerViewModel.registerUser(registerRequest)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObservers() {
        registerViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
        registerViewModel.isRegisterSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            }
        }
        registerViewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, "Registration Failed: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
