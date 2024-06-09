package com.dicoding.sibipediav2.viewmodel.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sibipediav2.data.remote.request.RegisterRequest
import com.dicoding.sibipediav2.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRegisterSuccess = MutableLiveData<Boolean>()
    val isRegisterSuccess: LiveData<Boolean> = _isRegisterSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    companion object {
        private const val TAG = "RegisterActivity"
    }

    fun registerUser(registerRequest: RegisterRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().registerUser(registerRequest)
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isRegisterSuccess.value = true
                } else {
                    _isRegisterSuccess.value = false
                    val errorBody = response.errorBody()?.string()
                    var errorMessage = "Unknown error"
                    if (!errorBody.isNullOrEmpty()) {
                        try {
                            val jsonObject = JSONObject(errorBody)
                            errorMessage = jsonObject.getString("message")
                            if (jsonObject.has("errors")) {
                                val errorsArray: JSONArray = jsonObject.getJSONArray("errors")
                                if (errorsArray.length() > 0) {
                                    errorMessage = errorsArray.getString(0)
                                }
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error parsing error message", e)
                        }
                    }
                    _errorMessage.value = errorMessage
                }
            } catch (e: HttpException) {
                _isLoading.value = false
                _isRegisterSuccess.value = false
                _errorMessage.value = e.message ?: "Unknown error"
            } catch (e: Exception) {
                _isLoading.value = false
                _isRegisterSuccess.value = false
                _errorMessage.value = e.message ?: "Unknown error"
            }
        }
    }
}
