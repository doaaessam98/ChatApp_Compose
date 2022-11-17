package com.example.chatapp.graphs

import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.chatapp.screen.groups.CreateGroupViewModel
import com.example.chatapp.screen.groups.GroupDetailsScreen
import com.example.chatapp.screen.groups.GroupsScreen
import com.example.chatapp.screen.groups.SelectGroupMemberScreen
import com.example.chatapp.screen.signup.SignupViewModel


fun NavGraphBuilder.CreateGroupNavGraph(
    modifier: Modifier, navController: NavHostController,
     viewModel: CreateGroupViewModel
){


    navigation(
        route=Graph.CREATE_GROUP,
        startDestination = HomeNavigationItem.Groups.route,
    ){
        composable(route= HomeNavigationItem.Groups.route){
            GroupsScreen(modifier = modifier, navController = navController)
        }
        composable(route= CreateGroupScreen.SelectMemberScreen.route){
            SelectGroupMemberScreen(modifier,navController = navController,viewModel)
        }
        composable(route= CreateGroupScreen.GroupDetailsScreen.route){
           GroupDetailsScreen(modifier,navController = navController,viewModel)
        }




    }
}

sealed class CreateGroupScreen(val route :String){

    object SelectMemberScreen:CreateGroupScreen("select_member_screen")
    object GroupDetailsScreen:CreateGroupScreen("group_details_screen")



}