package com.dicoding.sibipediav2.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserQuizResponse(
	@SerializedName("records") val records: Map<String, QuizRecord>
)

data class QuizRecord(
	@SerializedName("correctCount") val correctCount: Int,
	@SerializedName("createdAt") val createdAt: Long,
	@SerializedName("score") val score: Int,
	@SerializedName("totalQuestions") val totalQuestions: Int
)
