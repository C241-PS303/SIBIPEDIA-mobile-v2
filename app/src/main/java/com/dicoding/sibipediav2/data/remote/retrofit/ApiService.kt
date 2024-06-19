package com.dicoding.sibipediav2.data.remote.retrofit

import com.dicoding.sibipediav2.data.remote.request.Answer
import com.dicoding.sibipediav2.data.remote.response.AlphabetResponse
import com.dicoding.sibipediav2.data.remote.request.RegisterRequest
import com.dicoding.sibipediav2.data.remote.request.VerificationRequest
import com.dicoding.sibipediav2.data.remote.response.RegisterResponse
import com.dicoding.sibipediav2.data.remote.response.VocabularyResponse
import com.dicoding.sibipediav2.data.remote.response.ProfileResponse
import com.dicoding.sibipediav2.data.remote.response.QuestionResponse
import com.dicoding.sibipediav2.data.remote.response.QuizRecord
import com.dicoding.sibipediav2.data.remote.response.VerificationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest) : Response<RegisterResponse>

    @GET("sibi/alphabet")
    suspend fun getAlphabet() : Response<AlphabetResponse>

    @GET("sibi/words")
    suspend fun getVocabulary(): Response<VocabularyResponse>

    @GET("profile")
    fun getProfile() : Call<ProfileResponse>

    @GET("quiz/questions")
    suspend fun getQuestions() : QuestionResponse

    @POST("quiz/verify-answer")
    suspend fun verifyAnswers(@Body answers: List<Answer>): VerificationResponse

    @GET("quiz/user")
    suspend fun getUserQuizRecords(): Map<String, QuizRecord>
}