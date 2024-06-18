package com.dicoding.sibipediav2.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sibipediav2.data.remote.response.ProfileResponse
import com.dicoding.sibipediav2.data.remote.response.QuizRecord
import com.dicoding.sibipediav2.data.remote.response.UserData
import com.dicoding.sibipediav2.data.remote.retrofit.ApiConfig
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess: LiveData<Boolean> = _logoutSuccess

    private val _profile = MutableLiveData<UserData?>()
    val profile: MutableLiveData<UserData?> = _profile

    private val _userQuizRecords = MutableLiveData<Map<String, QuizRecord>>()
    val userQuizRecords: LiveData<Map<String, QuizRecord>> get() = _userQuizRecords

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _logoutSuccess.value = FirebaseAuth.getInstance().currentUser == null
    }

    fun fetchProfile() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getProfile()
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _profile.value = response.body()?.userData
                } else {
                    _profile.value = null
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _profile.value = null
            }
        })
    }

    fun fetchUserQuizRecords() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getUserQuizRecords()
                _userQuizRecords.postValue(response)
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
