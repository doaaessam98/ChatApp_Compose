package com.example.chatapp.screen.groups

import androidx.lifecycle.ViewModel
import com.example.chatapp.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private  val repository: IRepository):ViewModel() {
}