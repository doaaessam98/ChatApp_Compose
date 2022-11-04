package com.example.chatapp.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.graphs.AuthScreen
import com.example.chatapp.graphs.TopBarScreen

@Composable
fun ProfileScreen(navHostController: NavHostController,viewModel: ProfileViewModel= hiltViewModel()){
    Column (
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Button(onClick = {
             viewModel.logout()
            navHostController.navigate(AuthScreen.Login.route){
                popUpTo(TopBarScreen.Profile.route){
                    inclusive=true
                }
            }

        }) {
            Text(text = "Logout")
        }
    }

}