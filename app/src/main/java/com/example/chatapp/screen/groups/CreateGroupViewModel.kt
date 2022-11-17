package com.example.chatapp.screen.groups

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.repository.IRepository
import com.example.chatapp.model.GroupMember
import com.example.chatapp.model.InputField
import com.example.chatapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.chatapp.utils.Result
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CreateGroupViewModel @Inject constructor(private  val repository: IRepository):ViewModel() {


    private val _allUsers = MutableStateFlow<Result<List<User>>>(Result.Idle)
    val allUsers: StateFlow<Result<List<User>>>
        get() = _allUsers


    private val _selectedMember = MutableStateFlow<List<User>>(listOf())
    val selectedMember: StateFlow<List<User>>
        get() = _selectedMember



    private val _createGroup = MutableStateFlow<Result<Void>>(Result.Idle)
    val createGroup: StateFlow<Result<Void>>
        get() = _createGroup


    private val _groupNameInput = MutableStateFlow(InputField())
    val groupNameInput: StateFlow<InputField> = _groupNameInput

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        _allUsers.value = Result.Loading
        viewModelScope.launch {
            val result = repository.getUsers()
            _allUsers.emit(result)


        }
    }

    private fun addGroupMember(user: User) {
        _selectedMember.value = _selectedMember.value.plus(user)

    }


    fun removeGroupMember(user: User) {
        _selectedMember.value = _selectedMember.value.minus(user)

    }


    fun onUserClicked(user: User) {
        val isContain = isSelected(user)

        if(!isContain) {
            addGroupMember(user)


        } else {
            removeGroupMember(user)


        }
    }

    fun isSelected(user: User): Boolean {
        return _selectedMember.value.contains(user)
    }


    fun createNewGroup() {
        val groupMember = mapSelectedUsersToGroupMember()
        _createGroup.value = Result.Loading
        viewModelScope.launch {

           val result =  repository.createGroupChatRoom(_groupNameInput.value.input, groupMember)
            _createGroup.emit(result)
        }
    }


    private fun mapSelectedUsersToGroupMember(): List<String> {

        return _selectedMember.value.map { user ->
            user.id
        }


    }

  fun  onGroupNameChange(newName:String){

      if(newName.isBlank()) {
//          val message = requiredError
//          _groupNameInput.value = groupNameInput.value.copy(input = newName, isError = true, errorMessage = message)
      } else {
          _groupNameInput.value = _groupNameInput.value.copy(input = newName, isError = false)
      }






  }
}