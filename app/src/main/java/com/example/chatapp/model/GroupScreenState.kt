package com.example.chatapp.model

import com.google.firebase.auth.FirebaseUser

data class GroupScreenState(
    val data:List<Group>? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)
data class CreateGroupScreenState(
    val data:Void?= null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)

data class AuthScreenState(
    val data:FirebaseUser? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)


data class ScreenState(
    val data:List<User>? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)

data class ChatsScreenState(
    val data:FirebaseUser? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)
data class ForgotPasswordScreenState(
    val data:Boolean? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)