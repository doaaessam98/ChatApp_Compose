package com.example.chatapp.data.source.remote

import com.example.chatapp.model.User
import com.example.chatapp.utils.Result
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot

interface IUserData {
    val currentUser: FirebaseUser?
    suspend fun login(email: String,password: String): Result<FirebaseUser>
    suspend fun signup(name: String, password: String,email:String): Result<FirebaseUser>
    suspend fun restPassword( email:String):Result<Boolean>
     suspend fun getUsers(): Task<QuerySnapshot>
     suspend fun addFriend(currentUser: User,userFriendId:String):Task<Void>?
    suspend fun getCurrentUser(): Task<QuerySnapshot>
    fun isFriendOf(currentUser: User, otherUser: User): Boolean
//    suspend fun getAllFriends():Task<QuerySnapshot>

    fun logout()
}