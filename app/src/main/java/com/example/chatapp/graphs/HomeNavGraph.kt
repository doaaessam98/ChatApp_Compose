package com.example.chatapp.graphs

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.screen.chats.ChatsScreen
import com.example.chatapp.screen.friends.FriendsScreen
import com.example.chatapp.screen.groups.CreateGroupViewModel
import com.example.chatapp.screen.groups.GroupDetailsScreen
import com.example.chatapp.screen.groups.GroupsScreen
import com.example.chatapp.screen.groups.SelectGroupMemberScreen
import com.example.chatapp.screen.profile.ProfileScreen
import com.example.chatapp.screen.search.SearchScreen

@Composable
fun HomeNavGraph(modifier:Modifier,navController:NavHostController){
    val viewModel :CreateGroupViewModel = hiltViewModel()
    val groupNavController:NavHostController = rememberNavController()

    NavHost(navController = navController,
        route=Graph.HOME,
        startDestination = HomeNavigationItem.Chats.route,
    ){


        composable(route = HomeNavigationItem.Chats.route){
            ChatsScreen(modifier = modifier, navController = navController)
        }
        composable(route= HomeNavigationItem.Friends.route){
            FriendsScreen(modifier = modifier, navController = navController)
        }

        composable(route= HomeNavigationItem.Groups.route){
            GroupsScreen(modifier = modifier, navController = navController)
        }
   ///     CreateGroupNavGraph(modifier = modifier, navController = groupNavController,viewModel)

        composable(TopBarScreen.Search.route){
            SearchScreen(modifier = modifier, navController = navController)
        }
        composable(TopBarScreen.Profile.route){
            ProfileScreen(navController)
        }
        composable(route= CreateGroupScreen.SelectMemberScreen.route){
            SelectGroupMemberScreen(modifier,navController = navController,viewModel)
        }
        composable(route= CreateGroupScreen.GroupDetailsScreen.route){
            GroupDetailsScreen(modifier,navController = navController,viewModel)
        }

    }
}

sealed class HomeNavigationItem(
    val route:String,
    @StringRes
    val title:Int,
    @DrawableRes
    val icon:Int,


){
    object Chats:HomeNavigationItem(route="chats_screen", title = R.string.chats,icon= R.drawable.ic__chat_24)
    object Groups:HomeNavigationItem(route="groups_screen", R.string.groups, R.drawable.ic__groups_24)
    object Friends:HomeNavigationItem(route ="friends_screen", R.string.friends, R.drawable.ic_friend_24)
}

sealed class TopBarScreen(val route :String){
    object Profile:TopBarScreen(route ="profile_screen" )
    object Search:TopBarScreen(route ="search_screen" )
}