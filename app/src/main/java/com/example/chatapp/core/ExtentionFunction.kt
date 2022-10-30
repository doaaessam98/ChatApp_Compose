package com.example.chatapp.core

import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import java.util.regex.Pattern

fun String.isPhoneNumber(phoneNumber:String):Boolean{
  return   Patterns.PHONE.matcher(phoneNumber).matches()
 }

