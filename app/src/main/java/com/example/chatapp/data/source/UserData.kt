package com.example.chatapp.data.source

import com.example.chatapp.utils.Result
import com.example.chatapp.utils.await
import com.google.firebase.auth.*
import javax.inject.Inject

class UserData @Inject constructor(  private val firebaseAuth: FirebaseAuth):IUserData {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
    override suspend fun login(email: String,password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
             firebaseAuth
            Result .Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
           Result.Failure(e)
        }
    }

    override suspend fun signup(name: String, password: String,email:String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            return Result.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
           Result.Failure(e)
        }
    }








    override fun logout() {
        firebaseAuth.signOut()
    }

}