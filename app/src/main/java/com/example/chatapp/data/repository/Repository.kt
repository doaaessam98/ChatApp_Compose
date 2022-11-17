package com.example.chatapp.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.chatapp.data.source.remote.IUserData
import com.example.chatapp.model.Group
import com.example.chatapp.model.GroupMember
import com.example.chatapp.model.User
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.await
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class Repository @Inject constructor(private val  userData: IUserData) :IRepository{


    override val currentUser: FirebaseUser?
        get() = userData.currentUser
    override suspend fun login(email: String, password: String): Result<FirebaseUser> =userData.login(email,password)

    override suspend fun signup(
        name: String,
        password: String,
        email: String
    ): Result<FirebaseUser> =userData.signup(name,email,password)

    override fun logout() =userData.logout()

    override suspend fun forgotPassword(email: String) :Result<Boolean> =userData.restPassword(email)
    override suspend fun getCurrentUser():Result<User?> {
        return try {

             val result = userData.getCurrentUser().await()

             val user= result.toObjects(User::class.java)[0]
             // Constants.FRIENDS_LIST = user.friends as ArrayList<String>
              Result.Success(user)
        }catch (e:Exception){

            Result.Failure(e)
        }

    }

    override suspend fun searchForUser(searchString: String): Result<List<User>> {

        return try {
            val users = userData.getUsers().await()
            val usersObjects = users.toObjects(User::class.java)
            val allUser = usersObjects.filter { user ->
                    user.name.contains(searchString.trim(), ignoreCase = true)&&(user.id!=currentUser!!.uid)

            }

                Result.Success(allUser)


        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(e)
        }
    }

    override suspend fun addFriend(currentUser: User,userFriendId: String):Result<Void?>{
        return  try {

            val result= userData.addFriend(currentUser,userFriendId)?.await()

            //  Constants.FRIENDS_LIST.add(userFriendId)

              Result.Success(result)

        }catch (e:Exception){
           Result.Failure(e)
        }

    }

    override fun isFriendOf(currentUser: User, otherUser: User): Boolean =userData.isFriendOf(currentUser,otherUser)
    override suspend fun getAllFriends(): Result<List<User>> {
       return try {
               val currentUser = userData.getCurrentUser().await()
               val user= currentUser.toObjects(User::class.java)[0]
               if(user.friends.isNotEmpty()){
                   Log.e(TAG, "getAllFriends: ${user.friends}", )
               val result = userData.getAllFriends(user.friends).await()
                   Log.e(TAG, "getAllFriends: ${result.documents}", )
                   val friends = result.toObjects(User::class.java)
                   Log.e(TAG, "getAllFriends: $friends", )
                   Result.Success(friends)
               }else{
                   Result.Success(listOf())
               }


        }catch (e:Exception){
           Log.e(TAG, "getAllFriends: $e", )
           Result.Failure(e)
        }

    }

    override suspend fun removeFriend(friendId: String):Result<Void?> {
      return try {
          val currentUser = userData.getCurrentUser().await()
          val user = currentUser.toObjects(User::class.java)[0]
          val removeResult = userData.removeFriend(user, friendId)
          Result.Success(removeResult?.result )
      }catch (e:Exception){
         Result.Failure(e)
      }
    }

    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val result = userData.getUsers().await()
            val users = result.toObjects(User::class.java)
            Result.Success(users)
        }catch (e:Exception){
            Result.Failure(e)
        }
    }

    override suspend fun createGroupChatRoom(
        groupName: String,
        groupMembers: List<String>
    ): Result<Void> {
       return try {
            val members = groupMembers.plus(currentUser!!.uid)
           val result = userData.createGroupChatRoom(groupName,members).await()
           Result.Success(result)
       }catch (e:Exception){
           Result.Failure(e)
       }
    }


    override suspend fun getGroupsOfUser(): Result<List<Group>> {
        return  try {
             val result = userData.getGroupsOfUser(currentUser!!.uid).await()
            Log.e(TAG, "getGroupsOfUser: ${result.size()}", )
            Log.e(TAG, "getGroupsOfUser: ${result.isEmpty}", )

            val groups = result.toObjects(Group::class.java)
            Log.e(TAG, "getGroupsOfUser: ${groups}", )
             Result.Success(groups)
        }catch (e:Exception){
            Result.Failure(e)
        }
    }
}

