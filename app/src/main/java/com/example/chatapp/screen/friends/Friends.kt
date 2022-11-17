package com.example.chatapp.screen.friends

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.graphs.TopBarScreen
import com.example.chatapp.model.User
import com.example.chatapp.utils.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FriendsScreen(modifier: Modifier, navController: NavHostController,viewModel: FriendsViewModel= hiltViewModel()){


     Scaffold (
              floatingActionButton = { FloatingButton(modifier = modifier, icon = Icons.Default.PersonAdd) {
                     navController.navigate(TopBarScreen.Search.route)
              }},
               floatingActionButtonPosition = FabPosition.End )
     { contentPadding->

          FriendsList(modifier = modifier, navController = navController, viewModel = viewModel)
     }





}
@SuppressLint("SuspiciousIndentation")
@Composable
fun FriendsList(modifier: Modifier,viewModel: FriendsViewModel,navController: NavHostController){
    var friends :List<User> = listOf()
    var isSuccess by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    FriendsState(modifier,viewModel, onLoading = {isLoading=it}, onSuccess = { friendsList->
        isSuccess=true
        friends=friendsList

    })

        Box(modifier.fillMaxSize()) {

          if(friends.isNotEmpty()||isSuccess) {
              Log.e(TAG, "FriendsList: is not empty+${friends.size}", )
            LazyColumn {
                items(friends) { friend ->
                    FriendCard(
                        modifier = modifier,
                         onFriendClick =viewModel::onFriendClicked ,
                          onRemoveClicked =viewModel::onRemoveClicked,
                          friend = friend,
                        )
                }
            }
        }else {
              if(!isLoading) {
                  Log.e(TAG, "FriendsList: is loading", )
                  Column(modifier.align(Alignment.Center)) {
                      // GifImage(modifier)
                      Image(
                          painter = painterResource(R.drawable.empty_friends_list),
                          contentDescription = stringResource(id = R.string.empty_friends),

                      )
                  }
              }
          }

            ShowConfirmDialog(viewModel)



    }
}


@Composable
fun FriendsState(
    modifier: Modifier,
    viewModel: FriendsViewModel,
    onSuccess: (List<User>) -> Unit,
    onLoading:(Boolean)->Unit)
{
    val context = LocalContext.current
    val state =viewModel.allFriends.collectAsState()
    state.value.let { response->
        when(response){
            is Result.Loading-> {
                ShowLoading(modifier = modifier)
                onLoading(true)
            }
            is Result.Failure ->{
                response.exception.localizedMessage?.let { ShowToast(context =context , message = it) }
            }
            is Result.Success ->{
               
                Log.e(TAG, "FriendsState: ${response.result}", )
                onSuccess(response.result)
            }
 
            else -> {}
        }
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
        when(response){
            is Result.Loading-> {
                ShowLoading(modifier = modifier)

            }
            is Result.Failure ->{
                response.exception.localizedMessage?.let { ShowToast(context =context , message = it) }
            }
            is Result.Success ->{
                ShowToast(context = context, message = "${viewModel.friend?.friends}  is Delete from Friends")
               viewModel.getAllFriends()
            }

            else -> {}
        }
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
     viewModel: FriendsViewModel

) {
    val showDialogState: Boolean by viewModel.showDialog.collectAsState()


    if (showDialogState) {
        AlertDialog(
            onDismissRequest = {viewModel.onDialogDismiss()},
            confirmButton = {
                TextButton(onClick = {viewModel.onDialogConfirm()})
                { Text(text = "OK") }
            },
            dismissButton = {
                TextButton(onClick = {viewModel.onDialogDismiss()})
                { Text(text = "Cancel") }
            },
            title = { Text(text = "Please confirm") },
            text = { Text(text = "did  you sure you want to remove${viewModel.friend?.name} from friends") }
        )
    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 500)
@Composable
fun FriendsPreview(){
    FriendsScreen(modifier = Modifier, navController = rememberNavController())
}