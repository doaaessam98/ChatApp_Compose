package com.example.chatapp.data.source.remote

import com.example.chatapp.data.source.remote.IUserData
import com.example.chatapp.model.User
import com.example.chatapp.utils.Constants
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.await
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class UserData @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore

): IUserData {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
    override suspend fun login(email: String,password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

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
            result.user?.sendEmailVerification()
            addUserToDatabase(result.user!!).await()


            return Result.Success(result.user!!)
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
        return User(id = "", name = firebaseUser.displayName!!, email = firebaseUser.email!!)
    }

   override  suspend fun restPassword(email:String):Result<Boolean>{
      return  try {
            Firebase.auth.sendPasswordResetEmail(email).await()
             Result.Success(true)
        }catch (e:Exception){
           Result.Failure(e)
        }




    }

    override fun logout() {
        firebaseAuth.signOut()
    }

}