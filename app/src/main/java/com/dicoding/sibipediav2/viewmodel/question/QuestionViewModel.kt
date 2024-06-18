package com.dicoding.sibipediav2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sibipediav2.data.remote.request.Answer
import com.dicoding.sibipediav2.data.remote.response.QuestionsItem
import com.dicoding.sibipediav2.data.remote.response.VerificationResponse
import com.dicoding.sibipediav2.data.remote.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionViewModel : ViewModel() {

    private val _questions = MutableLiveData<List<QuestionsItem>>()
    val questions: LiveData<List<QuestionsItem>> get() = _questions

    private val _answers = MutableLiveData<MutableList<String?>>()
    val answers: LiveData<MutableList<String?>> get() = _answers

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        _answers.value = mutableListOf()
    }

    fun fetchQuestions() {
        _isLoading.value = true
        viewModelScope.launch {
            val apiService = ApiConfig.getApiService()
            try {
                val response = withContext(Dispatchers.IO) {
                    apiService.getQuestions()
                }
                Log.d("QuestionViewModel", "Fetched questions: $response")
                val questionItems = response.quiz.questions
                _questions.postValue(questionItems)
                _answers.value = MutableList(questionItems.size) { null }
            } catch (e: Exception) {
                Log.e("QuestionViewModel", "Error fetching questions", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun saveAnswer(index: Int, answer: String) {
        _answers.value?.set(index, answer)
        _answers.postValue(_answers.value)
    }

    fun submitAnswers(answers: List<Answer>) {
        _isLoading.value = true
        viewModelScope.launch {
            val apiService = ApiConfig.getApiService()
            try {
                val jsonRequest = Gson().toJson(answers)
                Log.d("QuestionViewModel", "Submitting answers: $jsonRequest")
                val response = withContext(Dispatchers.IO) {
                    apiService.verifyAnswers(answers)
                }
                val jsonResponse = Gson().toJson(response)
                Log.d("QuestionViewModel", "Received response: $jsonResponse")
                _score.postValue(response.data.score)
            } catch (e: Exception) {
                Log.e("QuestionViewModel", "Error submitting answers", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
