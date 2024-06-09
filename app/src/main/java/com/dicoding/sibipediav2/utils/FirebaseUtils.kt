package com.dicoding.sibipediav2.utils

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

suspend fun getFirebaseToken(): String? {
    val user = FirebaseAuth.getInstance().currentUser
    return user?.getIdToken(true)?.await()?.token
}