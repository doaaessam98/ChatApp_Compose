package com.example.chatapp.data.source

import com.example.chatapp.utils.Result
import com.google.firebase.auth.FirebaseUser

interface IUserData {
    val currentUser: FirebaseUser?
    suspend fun login(email: String,password: String): Result<FirebaseUser>
    suspend fun signup(name: String, phoneNumber: String,email:String): Result<FirebaseUser>
    fun logout()
}