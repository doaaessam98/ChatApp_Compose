package com.example.chatapp.utils

//sealed class Result<out R>{
//    data class Success<out R>(val result: R) :Result<R>()
//    data class Failure(val exception: Exception) :Result<Nothing>()
//    object Loading :Result<Nothing>()
//    object Idle :com.example.chatapp.utils.Result<Nothing>()
//
//}

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null,
    val b :Boolean?=false
) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T>(data: T? = null): Resource<T>(data)
}