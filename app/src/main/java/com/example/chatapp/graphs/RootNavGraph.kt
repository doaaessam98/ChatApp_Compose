package com.example.chatapp.graphs

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatapp.screen.home.HomeScreen
import com.example.chatapp.screen.profile.ProfileScreen
import com.example.chatapp.screen.search.SearchScreen
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@SuppressLint("SuspiciousIndentation")
@Composable
fun RootNavigationGraph(modifier: Modifier=Modifier,navController: NavHostController){
    val systemUiController: SystemUiController = rememberSystemUiController()

    NavHost(navController = navController,
             route =Graph.ROOT ,
             startDestination =Graph.AUTHENTICATION
    ){
        
        authNavGraph(modifier =modifier ,systemUiController, navController = navController)
        composable(route = Graph.HOME) {
            systemUiController.isStatusBarVisible=true
              HomeScreen()
         }




    }

}


object Graph{
    const val ROOT="root_graph"
    const val AUTHENTICATION="ath_graph"
    const val HOME="home_graph"
    const val CREATE_GROUP="group_graph"
    const val APPBAR="app_bar"


}