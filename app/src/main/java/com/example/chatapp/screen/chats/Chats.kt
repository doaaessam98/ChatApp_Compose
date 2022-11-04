package com.example.chatapp.screen.chats


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatsScreen(modifier: Modifier,navController: NavHostController){


    Scaffold(
         floatingActionButton = {FloatingActionButton(modifier,navController)},
         floatingActionButtonPosition = FabPosition.End
    ) {  paddintContent->
           LazyColumn(modifier = Modifier.fillMaxHeight()){
               //items()
           }
     }


}
@Composable
fun FloatingActionButton( modifier: Modifier,navController: NavHostController) {
        ExtendedFloatingActionButton(
            text = { stringResource(id = R.string.start_chat)},
            onClick = { Log.e(TAG, "FloatingActionButton: ", )},
            icon = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Start Chat"
                )
            },
        )
}
@Composable
fun ChatsItem(){

}
@Preview(showBackground = true)
@Composable
fun ChatsPreview(){
     ChatsScreen(modifier = Modifier, navController = rememberNavController())
}
