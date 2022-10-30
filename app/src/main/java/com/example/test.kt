package com.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main(){
    listOf(1, 2, 3).fold(0) { sum, element ->
        println(element)
        sum + element }


   val scp= CoroutineScope(Dispatchers.Main).launch {

   }
   }

