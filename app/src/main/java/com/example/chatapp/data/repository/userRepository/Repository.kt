package com.example.chatapp.data.repository.userRepository

import com.example.chatapp.data.source.remote.userData.IUserData
import com.example.chatapp.model.Group
import com.example.chatapp.model.User
import com.example.chatapp.utils.Resource
import com.example.chatapp.utils.await
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class Repository @Inject constructor(private val  userData: IUserData) : IRepository {


    override val currentUser: FirebaseUser?
        get() = userData.currentUser
    override suspend fun login(email: String, password: String): Resource<FirebaseUser> =userData.login(email,password)

    override suspend fun signup(
        name: String,
        password: String,
        email: String
    ): Resource<FirebaseUser> =userData.signup(name,email,password)

    override fun logout() =userData.logout()

    override suspend fun forgotPassword(email: String) :Resource<Boolean> =userData.restPassword(email)
    override suspend fun getCurrentUser():Resource<User?> {
        return try {

             val result = userData.getCurrentUser().await()

             val user= result.toObjects(User::class.java)[0]
             // Constants.FRIENDS_LIST = user.friends as ArrayList<String>
              Resource.Success(user)
        }catch (e:Exception){

            Resource.Error(e.localizedMessage)
        }

    }

    override suspend fun searchForUser(searchString: String): Resource<List<User>> {

        return try {
            val users = userData.getUsers().await()
            val usersObjects = users.toObjects(User::class.java)
            val allUser = usersObjects.filter { user ->
                    user.name.contains(searchString.trim(), ignoreCase = true)&&(user.id!=currentUser!!.uid)

            }

                Resource.Success(allUser)


        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage)
        }
    }

    override suspend fun addFriend(currentUser: User,userFriendId: String):Resource<Void?>{
        return  try {

            val result= userData.addFriend(currentUser,userFriendId)?.await()

            //  Constants.FRIENDS_LIST.add(userFriendId)

              Resource.Success(result)

        }catch (e:Exception){
            Resource.Error(e.localizedMessage)
        }

    }

    override fun isFriendOf(currentUser: User, otherUser: User): Boolean =userData.isFriendOf(currentUser,otherUser)
    override suspend fun getAllFriends(): Resource<List<User>> {
       return try {
               val currentUser = userData.getCurrentUser().await()
               val user= currentUser.toObjects(User::class.java)[0]
               if(user.friends.isNotEmpty()){
               val result = userData.getAllFriends(user.friends).await()
                   val friends = result.toObjects(User::class.java)
                   Resource.Success(friends)
               }else{
                   Resource.Success(listOf())
               }


        }catch (e:Exception){
           Resource.Error(e.localizedMessage)
        }

    }

    override suspend fun removeFriend(friendId: String):Resource<Void?> {
      return try {
          val currentUser = userData.getCurrentUser().await()
          val user = currentUser.toObjects(User::class.java)[0]
          val removeResult = userData.removeFriend(user, friendId)
          Resource.Success(removeResult?.result )
      }catch (e:Exception){
          Resource.Error(e.localizedMessage)
      }
    }

    override suspend fun getUsers(): Resource<List<User>> {
        return try {
            val result = userData.getUsers().await()
            val users = result.toObjects(User::class.java)
            Resource.Success(users)
        }catch (e:Exception){
            Resource.Error(e.localizedMessage)
        }
    }

    override suspend fun createGroupChatRoom(
        groupName: String,
        userList: List<String>
    ): Resource<Void> {
       return try {
            val members = userList.plus(currentUser!!.uid)
           val result = userData.createGroupChatRoom(groupName,members).await()
           Resource.Success(result)
       }catch (e:Exception){
           Resource.Error(e.localizedMessage)
       }
    }


    override suspend fun getGroupsOfUser(): Resource<List<Group>> {
        return  try {
             val result = userData.getGroupsOfUser(currentUser!!.uid).await()
            val groups = result.toObjects(Group::class.java)
             Resource.Success(groups)
        }catch (e:Exception){
            Resource.Error(e.localizedMessage)
        }
    }
}

