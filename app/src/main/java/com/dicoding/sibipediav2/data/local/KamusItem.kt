package com.dicoding.sibipediav2.data.local

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class KamusItem(
    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("image_url")
    val imageUrl: String

    ) : Parcelable

