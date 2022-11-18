package com.example.chatapp.screen.groups

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.graphs.CreateGroupScreen
import com.example.chatapp.model.Group
import com.example.chatapp.model.GroupScreenState
import com.example.chatapp.utils.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GroupsScreen(modifier: Modifier, navController: NavHostController , viewModel: GroupViewModel= hiltViewModel() ){

    val groupsState = viewModel.userGroups.collectAsState().value


    Scaffold(
        floatingActionButton = {
            FloatingButton(modifier, Icons.Default.GroupAdd) {
            navController.navigate(CreateGroupScreen.SelectMemberScreen.route)
        }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddintContent->

        GroupsScreenContent(modifier,groupsState,viewModel::onGroupClicked,viewModel::onRemoveClicked)

    }

}

@Composable
fun GroupsScreenContent(modifier: Modifier, state: GroupScreenState, onGroupClicked:(String)->Unit, onRemoveClicked:(String)->Unit) {

    state.data?.let {groups->

    LazyColumn {
        items(groups){ group->
            GroupItemContent(modifier,group,onGroupClicked,onRemoveClicked)
        }
    }

}
    if (state.hasError) {
        Text(
            text = state.errorMessage ?: "Something went wrong",
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )
        )
    }

    if (state.isLoading) {
        ShowLoading(modifier = modifier)
    }


}

@Composable
fun GroupItemContent(
    modifier: Modifier,
    group: Group,
    onGroupClick:(String)->Unit,
    onRemoveClicked:(String)->Unit) {

    Card(
        modifier.fillMaxWidth()
    ) {

        Box(
            modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 1.dp, top = 8.dp, bottom = 8.dp)
                .clickable { onGroupClick.invoke(group.groupId) }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {
                if(true) {
                    DefaultProfilePicture(modifier, "")

                } else {
                    ProfilePicture(picture = null, name ="")
                }
                Text(
                    text = group.groupName, style = MaterialTheme.typography.subtitle1,
                    modifier = modifier.padding(start = 16.dp)
                )
            }
            IconButton(
                onClick = {onRemoveClicked.invoke(group.groupId) },
                modifier.align(Alignment.CenterEnd))
            {
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


                )

            }


        }
    }
}
