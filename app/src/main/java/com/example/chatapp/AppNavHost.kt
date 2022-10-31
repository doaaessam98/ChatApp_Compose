package com.example.chatapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatapp.screen.signup.SignupViewModel
import com.example.chatapp.screen.fogetpassword.ForgotPasswordScreen
import com.example.chatapp.screen.signup.LoginScreen
import com.example.chatapp.screen.signup.SignupScreen
import com.example.chatapp.screen.home.HomeScreen
import com.example.chatapp.screen.splash.SplashScreen
import com.example.chatapp.utils.sealedClasses.Screen
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    val systemUiController: SystemUiController = rememberSystemUiController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Splash.route) {

            systemUiController.isStatusBarVisible = false
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            systemUiController.isStatusBarVisible = true

            LoginScreen(modifier,navController= navController)
        }
        composable(Screen.Signup.route) {
            SignupScreen(modifier=modifier,navController = navController)
        }
        composable(Screen.Home.route) {
            systemUiController.isStatusBarVisible = true

            HomeScreen(modifier, navController =  navController)
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(modifier, navController =  navController)
        }
    }
}