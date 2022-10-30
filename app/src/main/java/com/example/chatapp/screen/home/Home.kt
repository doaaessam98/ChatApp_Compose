package com.example.chatapp.screen.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.chatapp.screen.authScreens.AuthViewModel
import com.example.chatapp.ui.theme.ChatAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreen(modifier: Modifier =Modifier,
               viewModel: AuthViewModel?, navController: NavHostController
) {
  Text(text = "Hello${viewModel?.currentUser?.email}")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ChatAppTheme {
    }
}