package com.example.chatapp.screen.friends

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.repository.IRepository
import com.example.chatapp.model.User
import com.example.chatapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
             private val repository: IRepository)

    :ViewModel(){
    private  val _allFriends = MutableStateFlow<Result<List<User>>>(Result.Idle)
    val allFriends :StateFlow<Result<List<User>>> = _allFriends

    private  val _removeFriend = MutableStateFlow<Result<Void?>>(Result.Idle)
    val removeFriend :StateFlow<Result<Void?>> = _removeFriend


    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()
     var friend:User?=null

        init {
              getAllFriends()
        }

    @SuppressLint("SuspiciousIndentation")
     fun getAllFriends() {
        _allFriends.value=(Result.Loading)

        viewModelScope.launch {
         val result: Result<List<User>> =  repository.getAllFriends()
           _allFriends.emit(result)

         }
    }


    fun  onFriendClicked(friendId: String){

    }




    fun onRemoveClicked(userFriend: User) {
        showConfirmDialog()
        friend=userFriend

    }

    private fun showConfirmDialog(){
        _showDialog.value = true
    }
    fun onDialogConfirm() {
        _showDialog.value = false
        viewModelScope.launch {
          val result=repository.removeFriend(friend!!.id)
            _removeFriend.emit(result)
        }
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }





}