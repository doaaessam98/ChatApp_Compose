package com.example.chatapp.screen.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.repository.IRepository
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.chatapp.utils.Result
@HiltViewModel
class FriendsViewModel @Inject constructor(
             private val repository: IRepository)

    :ViewModel(){
    private  val _allFriends = MutableStateFlow<Result<List<User>>>(Result.Idle)
    val allFriends :MutableStateFlow<Result<List<User>>> = _allFriends

        init {
           // getAllFriends()
        }

//    private fun getAllFriends() {
//
//        viewModelScope.launch {
//            _allFriends.emit(Result.Loading)
//          val result =  repository.getCurrentUser()
//            _allFriends.emit(result)
//
//        }
  //  }
}