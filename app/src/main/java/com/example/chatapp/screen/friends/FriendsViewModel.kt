package com.example.chatapp.screen.friends

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.repository.userRepository.IRepository
import com.example.chatapp.model.GroupScreenState
import com.example.chatapp.model.ScreenState
import com.example.chatapp.model.User
import com.example.chatapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
             private val repository: IRepository
)

    :ViewModel(){
    private  val _allFriends = MutableStateFlow(ScreenState())
    val allFriends :StateFlow<ScreenState> = _allFriends


    private  val _removeFriend = MutableStateFlow(ScreenState())
    val removeFriend :StateFlow<ScreenState> = _removeFriend


    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()
     var friend:User?=null

        init {
              getAllFriends()
        }

    @SuppressLint("SuspiciousIndentation")
     fun getAllFriends() {


        viewModelScope.launch {
         val response=  repository.getAllFriends()
            //checkResponse(response, onLoading = {}, onError = {}, onSuccess = {})
            when (response) {
                is Resource.Loading -> {
                    _allFriends.value =ScreenState(isLoading = true)
                }
                is Resource.Error -> {
                    _allFriends.value =
                        ScreenState(
                            hasError = true,
                            errorMessage = response.message
                        )
                }
                is Resource.Success -> {
                    Log.e(ContentValues.TAG, "GroupsScreenContent: ", )
                    _allFriends.value= ScreenState(
                        data = response.data

                    )
                }


            }

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
          val response=repository.removeFriend(friend!!.id)
            when (response) {
                is Resource.Loading -> {
                    _removeFriend.value =ScreenState(isLoading = true)
                }
                is Resource.Error -> {
                    _allFriends.value =
                        ScreenState(
                            hasError = true,
                            errorMessage = response.message
                        )
                }
                is Resource.Success -> {
                    Log.e(ContentValues.TAG, "GroupsScreenContent: ", )
                    _removeFriend.value= ScreenState(
                      //  data = response

                    )
                }


            }
        }
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }





}