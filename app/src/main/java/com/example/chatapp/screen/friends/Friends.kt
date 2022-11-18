package com.example.chatapp.screen.friends

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.graphs.TopBarScreen
import com.example.chatapp.model.ScreenState
import com.example.chatapp.model.User
import com.example.chatapp.utils.DefaultProfilePicture
import com.example.chatapp.utils.FloatingButton
import com.example.chatapp.utils.ProfilePicture

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FriendsScreen(modifier: Modifier, navController: NavHostController,viewModel: FriendsViewModel= hiltViewModel()){

    val state =viewModel.allFriends.collectAsState().value
    val showDialogState: Boolean by viewModel.showDialog.collectAsState()

    Scaffold (
              floatingActionButton = { FloatingButton(modifier = modifier, icon = Icons.Default.PersonAdd) {
                     navController.navigate(TopBarScreen.Search.route)
              }},
               floatingActionButtonPosition = FabPosition.End )
     { contentPadding->

          FriendsList(modifier = modifier,
              state,
              onRemoveClicked=viewModel::onRemoveClicked,
              onFriendClicked=viewModel::onFriendClicked
          )


         ShowConfirmDialog(showDialogState,
          viewModel::onDialogDismiss,
         viewModel::onDialogConfirm,
         viewModel.friend?.name)

     }





}
@SuppressLint("SuspiciousIndentation")
@Composable
fun FriendsList(modifier: Modifier, friendsState: ScreenState,onRemoveClicked:(User)->Unit,onFriendClicked:(String)->Unit){
    friendsState.data?.let { friends->
        Box(modifier.fillMaxSize()) {
            LazyColumn {
                items(friends) { friend ->
                    FriendCard(
                        modifier = modifier,
                         onFriendClick =onFriendClicked ,
                          onRemoveClicked =onRemoveClicked,
                          friend = friend,
                        )
                }
            }
        }}

      if(friendsState.hasError){

      }




}



@Composable
fun DeleteFriendsState(
    modifier: Modifier,
    viewModel: FriendsViewModel
) {
    val context = LocalContext.current
    val state =viewModel.removeFriend.collectAsState()
    state.value.let { response->


    }
}

@Composable
fun FriendCard(modifier: Modifier, onRemoveClicked:(User)->Unit, onFriendClick:(String)->Unit, friend: User){

    Card(
       modifier.fillMaxWidth()
   ) {

    Box(
        modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 1.dp, top = 8.dp, bottom = 8.dp)
            .clickable { onFriendClick.invoke(friend.id) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            if(true) {
                DefaultProfilePicture(modifier, friend.name)

            } else {
                ProfilePicture(picture = null, name = friend.name)
            }
            Text(
                text = friend.name, style = MaterialTheme.typography.subtitle1,
                modifier = modifier.padding(start = 16.dp)
            )
        }
        IconButton(
            onClick = {onRemoveClicked.invoke(friend) },
            modifier.align(Alignment.CenterEnd))
        {
            Icon(
                imageVector = Icons.Default.PersonRemove,
                contentDescription = stringResource(
                    id = R.string.search_profile_image_description,
                    formatArgs = arrayOf()
                ),


                )

        }


    }
    }
}

@Composable
fun ShowConfirmDialog(
    showDialogState : Boolean,
    onDialogDismiss:()->Unit,
    onDialogConfirm:()->Unit,
    friendName:String?
) {


    if (showDialogState) {
        AlertDialog(
            onDismissRequest = {onDialogDismiss.invoke()},
            confirmButton = {
                TextButton(onClick = {onDialogConfirm.invoke()})
                { Text(text = "OK") }
            },
            dismissButton = {
                TextButton(onClick = {onDialogDismiss()})
                { Text(text = "Cancel") }
            },
            title = { Text(text = "Please confirm") },
            text = { Text(text = "did  you sure you want to remove${friendName} from friends") }
        )
    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 500)
@Composable
fun FriendsPreview(){
    FriendsScreen(modifier = Modifier, navController = rememberNavController())
}