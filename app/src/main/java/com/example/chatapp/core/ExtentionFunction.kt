package com.example.chatapp.core

import android.util.Patterns

fun String.isPhoneNumber(phoneNumber:String):Boolean{
  return   Patterns.PHONE.matcher(phoneNumber).matches()
 }

//  fun checkResponse(
//      response: Resource.,
//      onLoading:()->Unit,
//      onSuccess:(data:T)->Unit,
//      onError:(String?)->Unit){
//      when (response) {
//          is Resource.Loading -> {
//             onLoading()
//          }
//          is Resource.Error -> {
//             onError(response.message)
//          }
//          is Resource.Success -> {
//              onSuccess(re)
//          }
//
//
//      }
//
//  }

class T {

}





