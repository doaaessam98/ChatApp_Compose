package com.example.chatapp.model

 data class Group(
     val groupId:String= "",
     val admin:String= "",
     val groupName:String = "",
     var groupMember: List<String> = listOf()


 )
