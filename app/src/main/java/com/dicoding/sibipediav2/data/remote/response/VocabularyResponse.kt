package com.dicoding.sibipediav2.data.remote.response

data class VocabularyResponse(
    val status: Int,
    val message: String,
    val data: List<VocabularyItem>
)

data class VocabularyItem(
    val word: String,
    val image: String
)
