package com.example.chatapp.screen.chats


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.graphs.TopBarScreen
import com.example.chatapp.utils.FloatingButton


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatsScreen(modifier: Modifier,navController: NavHostController){


    Scaffold(
         floatingActionButton = {FloatingButton(modifier,Icons.Default.Chat) {
             navController.navigate(TopBarScreen.Search.route)
         }
         },
         floatingActionButtonPosition = FabPosition.End
    ) {  paddintContent->
           LazyColumn(modifier = Modifier.fillMaxHeight()){
               //items()
           }
     }


}

@Composable
fun ChatsItem(){

}
@Preview(showBackground = true)
@Composable
fun ChatsPreview(){
     ChatsScreen(modifier = Modifier, navController = rememberNavController())
}
