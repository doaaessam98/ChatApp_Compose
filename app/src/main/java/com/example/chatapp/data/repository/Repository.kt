package com.example.chatapp.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.chatapp.data.source.remote.IUserData
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
              Result.Success(user)
        }catch (e:Exception){

            Result.Failure(e)
        }

    }

    override suspend fun searchForUser(searchString: String): Result<List<User>> {

        return try {
            val users = userData.getUsers().await()
            Log.e(TAG, "searchForUser: ${users.size()}", )

                val usersObjects = users.toObjects(User::class.java)
            Log.e(TAG, "searchForUser2: ${usersObjects.get(0)}", )

            val allUser = usersObjects.filter { user ->
                    user.name.contains(searchString.trim(), ignoreCase = true)&&(user.id!=currentUser!!.uid)


                }
            Log.e(TAG, "searchForUser: ${allUser.size}", )
            Log.e(TAG, "searchForUser: ${allUser.size}", )
                Result.Success(allUser)


        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(e)
        }
    }

    override suspend fun addFriend(currentUser: User,userFriendId: String):Result<Void?>{
        return  try {
            val result= userData.addFriend(currentUser,userFriendId)?.await()
            Result.Success(result)

        }catch (e:Exception){
           Result.Failure(e)
        }

    }

    override fun isFriendOf(currentUser: User, otherUser: User): Boolean =userData.isFriendOf(currentUser,otherUser)

}

