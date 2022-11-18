package com.example.chatapp.screen.groups

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.repository.userRepository.IRepository
import com.example.chatapp.model.GroupScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.example.chatapp.utils.Resource
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private  val repository: IRepository):ViewModel() {

    val _userGroups = MutableStateFlow (GroupScreenState())
    val userGroups :StateFlow<GroupScreenState> = _userGroups

     init {
         Log.e(TAG, "init : ", )
         getUserGroup()
         Log.e(TAG, "init :${_userGroups.value} ", )

     }

    private fun getUserGroup() {
        _userGroups.value =GroupScreenState(isLoading = true)
        viewModelScope.launch {
            val response = repository.getGroupsOfUser()
            when (response) {
                is Resource.Loading -> {
                    _userGroups.value =GroupScreenState(isLoading = true)
                }
                is Resource.Error -> {
                    _userGroups.value =
                        GroupScreenState(
                        hasError = true,
                        errorMessage = response.message
                    )
                }
                is Resource.Success -> {
                   _userGroups.value= GroupScreenState(
                       data = response.data

                   )
                }


            }
        }
    }

  fun onRemoveClicked(groupId:String){

  }
     fun onGroupClicked(groupId: String){

     }
}