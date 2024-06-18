package com.dicoding.sibipediav2.data.remote.response

import com.google.gson.annotations.SerializedName

data class VerificationResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class VerificationResultsItem(

	@field:SerializedName("questionId")
	val questionId: String,

	@field:SerializedName("userAnswer")
	val userAnswer: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("options")
	val options: List<String>,

	@field:SerializedName("correctAnswer")
	val correctAnswer: String,

	@field:SerializedName("questionText")
	val questionText: String,

	@field:SerializedName("isCorrect")
	val isCorrect: Boolean
)

data class Data(

	@field:SerializedName("score")
	val score: Int,

	@field:SerializedName("verificationResults")
	val verificationResults: List<VerificationResultsItem>
)
