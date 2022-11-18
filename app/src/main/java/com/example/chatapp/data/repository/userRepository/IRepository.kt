package com.example.chatapp.data.repository.userRepository

import com.example.chatapp.model.Group
import com.example.chatapp.model.User
import com.google.firebase.auth.FirebaseUser
import  com.example.chatapp.utils.Resource

interface IRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String,password: String): Resource<FirebaseUser>
    suspend fun signup(name: String,password : String,email:String): Resource<FirebaseUser>
    suspend fun forgotPassword(email: String):Resource<Boolean>
    suspend fun searchForUser(searchString: String): Resource<List<User>>
    suspend fun addFriend(currentUser: User,userFriendId:String): Resource<Void?>
    suspend fun getCurrentUser(): Resource<User?>
    suspend fun removeFriend(friendId: String):Resource<Void?>
    suspend fun getAllFriends():Resource<List<User>>
    suspend fun getUsers(): Resource<List<User>>

    fun isFriendOf(currentUser: User, otherUser: User): Boolean
    suspend fun createGroupChatRoom(groupName: String, userList: List<String>):Resource <Void>
      suspend fun getGroupsOfUser(): Resource <List<Group>>

    fun logout()

}