package com.example.chatapp.data.source.remote.userData

import com.example.chatapp.model.User
import com.example.chatapp.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot

interface IUserData {

      val currentUser: FirebaseUser?
      suspend fun login(email: String,password: String): Resource<FirebaseUser>
      suspend fun signup(name: String, password: String,email:String): Resource<FirebaseUser>
      suspend fun restPassword( email:String):Resource<Boolean>
      suspend fun getUsers(): Task<QuerySnapshot>
      suspend fun addFriend(currentUser: User,userFriendId:String):Task<Void>?
      suspend fun getCurrentUser(): Task<QuerySnapshot>
      suspend fun getAllFriends(friendsIds: List<String>):Task<QuerySnapshot>
      suspend fun removeFriend(user: User, friendId: String): Task<Void>?
      fun isFriendOf(currentUser: User, otherUser: User): Boolean
      suspend fun createGroupChatRoom(groupName: String, userList: List<String>): Task<Void>
        suspend fun getGroupsOfUser(userUid: String): Task<QuerySnapshot>
      fun logout()
}