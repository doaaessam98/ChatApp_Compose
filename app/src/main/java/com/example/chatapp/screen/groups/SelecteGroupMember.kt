package com.example.chatapp.screen.groups

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.graphs.CreateGroupScreen
import com.example.chatapp.model.User
import com.example.chatapp.utils.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SelectGroupMemberScreen(modifier: Modifier,navController: NavHostController,viewModel: CreateGroupViewModel){



    val  context = LocalContext.current
    val selectedMember = viewModel.selectedMember.collectAsState().value.isNotEmpty()
    var isEmptyList by remember { mutableStateOf(false) }

Scaffold (

    topBar = {CustomTopBar(modifier,"New Group","Add participants"){
                 navController.popBackStack()
              } },
    floatingActionButton = { FloatingButton(modifier = modifier, icon = Icons.Default.ArrowForward)
    {
        if(selectedMember)
        {
            navController.navigate(CreateGroupScreen.GroupDetailsScreen.route)
        }else{
            isEmptyList = true
        }
    } }
){
    SelectGroupMemberScreenContent(modifier,viewModel)

     if(isEmptyList) {
         ShowToast(context = context, message = "At least 1 member must  be selected")
         isEmptyList = false
     }
 }




}








@Composable
fun SelectGroupMemberScreenContent(modifier: Modifier,viewModel: CreateGroupViewModel) {
  Column(modifier = modifier) {
      SelectedMember(modifier,viewModel)
       AllUsers(modifier,viewModel)


  }
}


@Composable
fun SelectedMember(modifier: Modifier,viewModel: CreateGroupViewModel) {

    val selectedMember = viewModel.selectedMember.collectAsState().value

    if(selectedMember.isNotEmpty()){
        LazyRow{
             items(selectedMember){ user->
                 SelectedMemberContent(modifier = modifier, user = user,viewModel::removeGroupMember)
            }
   }
    }
    else{

    }

}
@Composable
fun SelectedMemberContent(
    modifier: Modifier,
    user: User,
    onRemoveClick: (User) -> Unit
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onRemoveClick.invoke(user)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Box() {
            if(true) {
                DefaultProfilePicture(modifier, "")

            } else {
                ProfilePicture(picture = null, "")
            }


            Icon(

                imageVector = Icons.Rounded.Cancel,
                tint = Color.White,
                contentDescription = stringResource(
                    id = R.string.search_profile_image_description,
                    formatArgs = arrayOf()
                ),

                modifier = modifier
                    .requiredSize(16.dp)
                    .padding(0.dp)
                    .background(color = Color(0xFF407BFF), shape = CircleShape)
                    .align(Alignment.BottomEnd)

            )


        }



        Text(text = user.name)
    }

}


@SuppressLint("SuspiciousIndentation")
@Composable
fun AllUsers(modifier: Modifier ,viewModel: CreateGroupViewModel) {
    val  context = LocalContext.current

    var users :List<User> = listOf()
    val usersState =viewModel.allUsers.collectAsState().value
    usersState.let {  response->
     when(response){
         is Result.Loading->{
           ShowLoading(modifier = modifier)
         }
         is Result.Failure ->{
           ShowToast(context =context , message = response.exception.localizedMessage)
         }
         is Result.Success->{
             users= response.result
         }
         else -> {}
     }
    }
     if(users.isNotEmpty() && usersState !=Result.Loading){
     LazyColumn{
         items(users){user->
            val isSelected =  viewModel.selectedMember.collectAsState().value.contains(user)
              AllUserContent(modifier,user,viewModel::onUserClicked, isSelected)
         }
     }
     }else{
          if(usersState !=Result.Loading){
              //
          }
     }
}

@Composable
fun AllUserContent(
    modifier: Modifier,
    user: User,onClick:(User)->Unit,
    isSelected:Boolean
){

    Card(
        modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(user)

            }
    ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                   modifier = modifier.padding(16.dp)
            ) {
                Box {
                    if(true) {
                        DefaultProfilePicture(modifier, "")

                    }
                    else {
                        ProfilePicture(picture = null, "")
                    }

                  if(isSelected){
                      Icon(

                          imageVector = Icons.Rounded.Done,
                          tint = Color.White,
                          contentDescription = stringResource(
                              id = R.string.search_profile_image_description,
                              formatArgs = arrayOf()
                          ),

                          modifier = modifier
                              .requiredSize(16.dp)
                              .padding(0.dp)
                              .background(color = Color(0xFF407BFF), shape = CircleShape)
                              .align(Alignment.BottomEnd)

                      )


                }
                }

                Text(
                    text = user.name, style = MaterialTheme.typography.subtitle1,
                    modifier = modifier.padding(start = 16.dp)
                )
            }




    }
}



@Preview(showBackground = true, widthDp = 250, heightDp = 420)
@Composable
fun SelectedMemberPreview(){
   //SelectGroupMemberScreen(modifier = Modifier, navController = rememberNavController())
}
