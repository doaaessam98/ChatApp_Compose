package com.example.chatapp.screen.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun FriendsScreen(modifier: Modifier, navController: NavHostController){

     LazyColumn(){

     }


}

@Composable
fun FriendCard(modifier: Modifier,navController: NavHostController){
    Card() {
        Row() {
//          Image(painter = , contentDescription = )
          Text(text = "")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FriendsPreview(){
    FriendsScreen(modifier = Modifier, navController = rememberNavController())
}