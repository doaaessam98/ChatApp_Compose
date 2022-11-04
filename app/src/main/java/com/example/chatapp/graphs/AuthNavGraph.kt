package com.example.chatapp.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.chatapp.screen.fogetpassword.ForgotPasswordScreen
import com.example.chatapp.screen.signup.LoginScreen
import com.example.chatapp.screen.signup.SignupScreen
import com.example.chatapp.screen.splash.SplashScreen
import com.google.accompanist.systemuicontroller.SystemUiController


fun NavGraphBuilder.authNavGraph(modifier: Modifier,systemUiController:SystemUiController, navController: NavHostController){
   navigation(
       route = Graph.AUTHENTICATION,
       startDestination = AuthScreen.Splash.route,
   ){

       composable(AuthScreen.Splash.route) {

           systemUiController.isStatusBarVisible = false
           SplashScreen(navController = navController)
       }
       composable(AuthScreen.Login.route) {
           systemUiController.isStatusBarVisible = true

           LoginScreen(modifier,navController= navController)
       }
       composable(AuthScreen.Signup.route) {
           SignupScreen(modifier=modifier,navController = navController)
       }

       composable(AuthScreen.ForgotPassword.route) {
           ForgotPasswordScreen(modifier, navController =  navController)
       }
   }
}

sealed class AuthScreen(val route :String){

    object Signup: AuthScreen(route = "signup_screen")
    object Login: AuthScreen(route = "login_screen")
    object Splash: AuthScreen(route = "splash_screen")
    object ForgotPassword:AuthScreen(route ="forgot_password_screen" )



}