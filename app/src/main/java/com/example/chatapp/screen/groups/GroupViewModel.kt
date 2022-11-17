package com.example.chatapp.screen.groups

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.repository.IRepository
import com.example.chatapp.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.example.chatapp.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private  val repository: IRepository):ViewModel() {

  private  val _userGroups = MutableStateFlow<Result<List<Group>>> (Result.Idle)
    val userGroups :StateFlow<Result<List<Group>>> = _userGroups

     init {
         Log.e(TAG, "init : ", )
         getUserGroup()
         Log.e(TAG, "init :${_userGroups.value} ", )

     }

    private fun getUserGroup() {
        _userGroups.value =Result.Loading
        viewModelScope.launch {
              val result = repository.getGroupsOfUser()
              _userGroups.emit(result)
            delay(300)
            _userGroups.emit(Result.Idle)

        }
    }

  fun onRemoveClicked(groupId:String){

  }
     fun onGroupClicked(groupId: String){

     }
}