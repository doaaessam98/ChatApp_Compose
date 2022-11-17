package com.example.chatapp.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.chatapp.model.Group
import com.example.chatapp.model.GroupMember
import com.example.chatapp.model.User
import com.example.chatapp.utils.Constants
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.await
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import java.util.*
import javax.inject.Inject


class UserData @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore

): IUserData {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            Result.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(e)
        }
    }

    override suspend fun signup(
        name: String,
        password: String,
        email: String
    ): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(name).build()
            )?.await()
            //result.user?.sendEmailVerification()
            addUserToDatabase(result.user!!).await()
            Result.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(e)
        }
    }


    private fun addUserToDatabase(user: FirebaseUser): Task<Void> {
        val user = firebaseUserToUser(user)
        return firestore.collection(Constants.USER_COLLECTION)
            .document(user.id)
            .set(user)

    }

    private fun firebaseUserToUser(firebaseUser: FirebaseUser): User {
        return User(
            id = firebaseUser.uid,
            name = firebaseUser.displayName!!,
            email = firebaseUser.email!!
        )
    }

    override suspend fun restPassword(email: String): Result<Boolean> {
        return try {
            Firebase.auth.sendPasswordResetEmail(email).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Failure(e)
        }


    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun getUsers(): Task<QuerySnapshot> {
        return firestore
            .collection(Constants.USER_COLLECTION)
            .get()


    }

    override suspend fun addFriend(currentUser: User, userFriendId: String): Task<Void>? {


        if (currentUser.friends.contains(userFriendId)) {
            return null
        }
        val updateUserFriends = currentUser.friends.plus(userFriendId)
        return firestore.collection(Constants.USER_COLLECTION)
            .document(currentUser.id).update(Constants.USER_FRIENDS, updateUserFriends)
    }

    override suspend fun getCurrentUser(): Task<QuerySnapshot> {
        val uid = currentUser!!.uid

       return  firestore
            .collection(Constants.USER_COLLECTION)
            .whereEqualTo("id", uid)
            .get()

    }


    override fun isFriendOf(currentUser: User, otherUser: User): Boolean {

        for(UserId in currentUser.friends) {
            if(UserId==otherUser.id) {
                return true
            }
        }
        return false

    }

    override suspend fun getAllFriends(friendsIds: List<String>): Task<QuerySnapshot> {

        return firestore
            .collection(Constants.USER_COLLECTION)
            .whereIn(Constants.USER_UID, friendsIds)
            .get()
    }
    override suspend  fun removeFriend(user: User, friendId: String): Task<Void>? {

        val newFriends = user.friends - friendId
        return firestore
            .collection(Constants.USER_COLLECTION)
            .document(user.id)
            .update(Constants.USER_FRIENDS, newFriends)
    }


    override  suspend fun createGroupChatRoom(groupName: String, userList: List<String>): Task<Void> {
        val chatRoom = Group(
            groupId =UUID.randomUUID().toString(),
            groupName =groupName ,
            groupMember = userList,
            admin = currentUser!!.uid

        )

        return firestore
            .collection(Constants.GROUPS_COLLECTION)
            .document(chatRoom.groupId)
            .set(chatRoom)
    }


   override  suspend fun getGroupsOfUser(userUid: String): Task<QuerySnapshot> {

        return firestore
            .collection(Constants.GROUPS_COLLECTION)
            .whereArrayContains(Constants.GROUP_MEMBERS,userUid)
            .get()
    }

}