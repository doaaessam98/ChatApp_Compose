package com.example.chatapp.screen.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.repository.userRepository.IRepository
import com.example.chatapp.model.*
import com.example.chatapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CreateGroupViewModel @Inject constructor(private  val repository: IRepository):ViewModel() {


    private val _allUsers = MutableStateFlow(ScreenState())
    val allUsers: StateFlow<ScreenState>
        get() = _allUsers


    private val _selectedMember = MutableStateFlow<List<User>>(listOf())
    val selectedMember: StateFlow<List<User>>
        get() = _selectedMember



    private val _createGroup = MutableStateFlow(CreateGroupScreenState())
    val createGroup: StateFlow<CreateGroupScreenState>
        get() = _createGroup


    private val _groupNameInput = MutableStateFlow(InputField())
    val groupNameInput: StateFlow<InputField> = _groupNameInput

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            val response = repository.getUsers()
            when (response) {
                is Resource.Loading -> {
                    _allUsers.value =ScreenState(isLoading = true)
                }
                is Resource.Error -> {
                    _allUsers.value =
                        ScreenState(
                            hasError = true,
                            errorMessage = response.message
                        )
                }
                is Resource.Success -> {
                    _allUsers.value=ScreenState(
                        data = response.data

                    )
                }


            }


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
        viewModelScope.launch {
            val response =  repository.createGroupChatRoom(_groupNameInput.value.input, groupMember)
            when (response) {
                is Resource.Loading -> {
                    _createGroup.value =CreateGroupScreenState(isLoading = true)
                }
                is Resource.Error -> {
                   _createGroup.value =
                        CreateGroupScreenState(
                            hasError = true,
                            errorMessage = response.message
                        )
                }
                is Resource.Success -> {
                    _createGroup.value=CreateGroupScreenState(
                         data = response.data

                    )
                }


            }
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