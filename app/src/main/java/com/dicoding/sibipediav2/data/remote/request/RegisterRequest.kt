package com.dicoding.sibipediav2.data.remote.request

data class RegisterRequest (
    val username: String,
    val email: String,
    val password: String,
    val displayName: String,
    val phoneNumber: String
)

