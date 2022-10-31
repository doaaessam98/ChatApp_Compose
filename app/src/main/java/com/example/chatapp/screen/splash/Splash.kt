package com.example.chatapp.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.chatapp.R
import com.example.chatapp.screen.login.LoginViewModel
import com.example.chatapp.utils.sealedClasses.Screen

@Composable
fun SplashScreen(navController: NavHostController, viewModel: LoginViewModel? = hiltViewModel()) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.logo))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress }
        )
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
             if (viewModel?.currentUser !=null) {
                 navController.navigate(Screen.Home.route){
                     popUpTo(Screen.Splash.route){
                         inclusive=true
                     }
                 }
             }
            else{
                 navController.navigate(Screen.Login.route){
                     popUpTo(Screen.Splash.route){
                         inclusive=true
                     }
                 }
            }
        }
    }
}

@Preview
@Composable
fun SplashPreview(){
    SplashScreen(navController = rememberNavController(), viewModel =null )
}



/**
 *
 *
 *
 *
 *
 *
 *
 * */