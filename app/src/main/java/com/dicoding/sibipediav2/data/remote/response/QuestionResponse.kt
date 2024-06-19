package com.dicoding.sibipediav2.data.remote.response

import com.google.gson.annotations.SerializedName

data class QuestionResponse(

	@field:SerializedName("quiz")
	val quiz: Quiz
)

data class QuestionsItem(

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("options")
	val options: List<String>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("questionText")
	val questionText: String
)

data class Quiz(

	@field:SerializedName("questions")
	val questions: List<QuestionsItem>
)
