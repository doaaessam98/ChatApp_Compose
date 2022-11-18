package com.example.chatapp.screen.search

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.repository.userRepository.IRepository
import com.example.chatapp.model.InputField
import com.example.chatapp.model.GroupScreenState
import com.example.chatapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.example.chatapp.utils.SearchValues
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: IRepository,
    application: Application
)

    :AndroidViewModel(application){

    private val _searchBar = MutableStateFlow(InputField())
    val searchBar: StateFlow<InputField> = _searchBar

    private val _searchResults = MutableStateFlow(GroupScreenState())

    val searchResults: StateFlow<GroupScreenState> = _searchResults

    private var searchStarterTimer: CountDownTimer? = null
     var currentUser:User?=null
        private set
     init {
          getCurrentUser()
         val defaultSearchString = "a"
          searchForUsers(defaultSearchString)
     }

    private fun getCurrentUser() {

        viewModelScope.launch {
            repository.getCurrentUser().let { userResult->


            }
        }

    }

    @SuppressLint("StringFormatInvalid")
    fun onSearchValueChange(newValue:String){
        when {
            newValue.length < SearchValues.MIN_LENGTH -> {

                _searchBar.value = searchBar.value.copy(input = newValue, isError = true)
            }
            newValue.length > SearchValues.MAX_LENGTH -> {

                _searchBar.value = searchBar.value.copy(input = newValue, isError = true)
            }
            else -> {
                _searchBar.value = searchBar.value.copy(input = newValue, isError = false)
                searchStarterTimer?.cancel()
                searchStarterTimer = object : CountDownTimer(SearchValues.SEARCH_DELAY, SearchValues.SEARCH_DELAY) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        searchForUsers(searchBar.value.input)
                    }
                }.start()
            }
        }


        searchForUsers(newValue)
    }

    private fun searchForUsers(searchString: String) =viewModelScope.launch {
        //_searchResults.value=Resource.Loading
        val result = repository.searchForUser(searchString)
        // _searchResults.value=result


    }

    fun onAddFriendClicked(userFriendId:String, isFriendState:MutableState<Boolean>){
        viewModelScope.launch {
            repository.addFriend(currentUser!!,userFriendId)
             isFriendState.value=true

            }
        }



    fun isFriendOf(otherUser: User): Boolean {
        
        return if(currentUser!=null) {
            repository.isFriendOf(currentUser!!, otherUser)
        } else {
            Log.e(TAG, "isFriendOf: user is null", )
            isFriendOf(otherUser)
        }

    }

}