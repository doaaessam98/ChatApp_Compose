package com.example.chatapp.screen.groups

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.graphs.CreateGroupScreen
import com.example.chatapp.graphs.HomeNavigationItem
import com.example.chatapp.model.CreateGroupScreenState
import com.example.chatapp.model.InputField
import com.example.chatapp.utils.*
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GroupDetailsScreen(modifier:Modifier,navController: NavHostController,viewModel:CreateGroupViewModel){
    val  context = LocalContext.current
    val groupName = viewModel.groupNameInput.collectAsState().value
    var isGroupNameEmpty by remember { mutableStateOf(false) }
    val createGroupState = viewModel.createGroup.collectAsState().value
    val selectedMemberState = viewModel.selectedMember.collectAsState()


    Scaffold (

     topBar = {
            CustomTopBar(modifier,"New Group ","Add subject"){
            navController.popBackStack()
        } },
        floatingActionButton = { FloatingButton(modifier = modifier, icon = Icons.Default.Done)
        {
            if(groupName.input.isNotEmpty()) {
                viewModel.createNewGroup()
             } else{
               isGroupNameEmpty = true
            }
        } }
    ){  contentPadding->

        Column() {

            GroupNameAndImage(modifier,viewModel.groupNameInput,viewModel::onGroupNameChange)
            SelectedMember(modifier,selectedMemberState,viewModel::removeGroupMember)

        }

        CreateGroupState(createGroupState,navController)

        if(isGroupNameEmpty) {
            ShowToast(context = context, message = "Group name is  must  and image is option ")
            isGroupNameEmpty = false
        }

    }

}



@Composable
fun CreateGroupState(createGroupState: CreateGroupScreenState,navController: NavHostController) {
    createGroupState.data?.let {
      navigateToGroupScreen(navController)
    }
    if(createGroupState.hasError){

    }

}


@Composable
fun GroupNameAndImage(
    modifier: Modifier,
    inputField: StateFlow<InputField>,
    onGroupNameChange:(String)->Unit) {


        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
               , modifier = modifier.padding(16.dp)

           ) {
            Box(
                modifier
                    .padding(end = 16.dp)
                    .clickable { }
               ) {
                if(true) {
                    DefaultProfilePicture(modifier, "")

                } else {
                    ProfilePicture(picture = null, "")
                }


                Icon(

                    imageVector = Icons.Rounded.Add,
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
              GroupNameField(modifier,inputField,onGroupNameChange)
        }
    }



@Composable
fun GroupNameField(
    modifier: Modifier,
    inputField: StateFlow<InputField>,
    onValueChanged: (String) -> Unit,
    ) {
        val focusManager = LocalFocusManager.current
        val groupname =inputField.collectAsState()

       Column(modifier.padding(end = 16.dp)) {

            TextField(
                value = groupname.value.input,
                onValueChange = {
                    onValueChanged(it)
                },
                label = { Text(text =if(groupname.value.isError) "error"  else stringResource(id = R.string.enter_group_name)) },
                maxLines = 1,
                textStyle = TextStyle(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Email),
                isError= groupname.value.isError,

                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down) }),
                modifier = modifier.padding(top = 16.dp)


            )
        }
        if (groupname.value.isError) {
            Text(text = groupname.value.errorMessage,
                style = TextStyle(color = MaterialTheme.colors.error)
            )

        }
}


@Preview(showBackground = true, widthDp = 220, heightDp = 420)
@Composable
fun GroupDetailsPreview(){
  //  GroupDetailsScreen(navController = rememberNavController(), modifier = Modifier)
}
   private  fun navigateToGroupScreen(navController: NavHostController) {
       navController.navigate(HomeNavigationItem.Groups.route) {
           popUpTo(CreateGroupScreen.GroupDetailsScreen.route) {
             //  inclusive = true
           }
       }
   }