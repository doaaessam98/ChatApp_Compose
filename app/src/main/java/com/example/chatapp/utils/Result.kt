package com.example.chatapp.utils

sealed class Result<out R>{
    data class Success<out R>(val result: R) :Result<R>()
    data class Failure(val exception: Exception) :Result<Nothing>()
    object Loading :Result<Nothing>()
}

sealed class Result1{
    data class Success<out R>(val result: R) :Result1()
    data class Failure(val exception: Exception) :Result1()
    object Loading :Result1()
}