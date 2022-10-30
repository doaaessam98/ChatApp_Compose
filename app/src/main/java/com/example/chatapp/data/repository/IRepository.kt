package com.example.chatapp.data.repository

import com.google.firebase.auth.FirebaseUser
import  com.example.chatapp.utils.Result
interface IRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String,password: String): Result<FirebaseUser>
    suspend fun signup(name: String,password : String,email:String): Result<FirebaseUser>
    fun logout()
}