package com.dicoding.sibipediav2.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("userData")
	val userData: UserData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class UserData(

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("email")
	val email: String,
)
