package com.dicoding.sibipediav2.data.remote.response

data class AlphabetResponse(
    val signedUrl: SignedUrl
)

data class SignedUrl(
    val status: Int,
    val message: String,
    val data: List<AlphabetItem>
)

data class AlphabetItem(
    val name: String,
    val image: String
)
