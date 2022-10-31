package com.example.chatapp.utils.sealedClasses


 sealed class Screen(val route :String){
     object Home: Screen(route = "home_screen")
     object Signup: Screen(route = "signup_screen")
     object Login: Screen(route = "login_screen")
     object Splash: Screen(route = "splash_screen")
     object ForgotPassword:Screen(route ="forgot_password_screen" )
 }