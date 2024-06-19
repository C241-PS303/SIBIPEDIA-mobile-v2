package com.dicoding.sibipediav2.data.remote.request

data class VerificationRequest(
    val answers: List<Answer>
)

data class Answer(
    val questionId: String,
    val userAnswer: String
)
