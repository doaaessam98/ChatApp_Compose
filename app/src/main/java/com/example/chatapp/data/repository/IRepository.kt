package com.example.chatapp.data.repository

import com.example.chatapp.model.Group
import com.example.chatapp.model.GroupMember
import com.example.chatapp.model.User
import com.google.firebase.auth.FirebaseUser
import  com.example.chatapp.utils.Result
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface IRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String,password: String): Result<FirebaseUser>
    suspend fun signup(name: String,password : String,email:String): Result<FirebaseUser>
    suspend fun forgotPassword(email: String):Result<Boolean>
    suspend fun searchForUser(searchString: String): Result<List<User>>
    suspend fun addFriend(currentUser: User,userFriendId:String): Result<Void?>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun removeFriend(friendId: String):Result<Void?>
    suspend fun getAllFriends():Result<List<User>>
    suspend fun getUsers(): Result<List<User>>

    fun isFriendOf(currentUser: User, otherUser: User): Boolean
    suspend fun createGroupChatRoom(groupName: String, userList: List<String>):Result <Void>
      suspend fun getGroupsOfUser(): Result <List<Group>>

    fun logout()

}