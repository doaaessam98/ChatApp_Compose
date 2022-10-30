package com.example.chatapp.data.repository

import com.example.chatapp.data.source.IUserData
import com.example.chatapp.utils.Result
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class Repository @Inject constructor(private val  userData:IUserData) :IRepository{


    override val currentUser: FirebaseUser?
        get() = userData.currentUser
    override suspend fun login(email: String, password: String): Result<FirebaseUser> =userData.login(email,password)

    override suspend fun signup(
        name: String,
        password: String,
        email: String
    ): Result<FirebaseUser> =userData.signup(name,email,password)

    override fun logout() =userData.logout()


}