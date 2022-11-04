package com.example.chatapp.screen.profile

import androidx.lifecycle.ViewModel
import com.example.chatapp.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: IRepository):ViewModel() {

    fun logout(){
        repository.logout()
    }
}