package com.example.chatapp.screen.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.chatapp.screen.signup.SignupViewModel
import com.example.chatapp.ui.theme.ChatAppTheme


@Composable
fun HomeScreen(modifier: Modifier =Modifier,
                navController: NavHostController
) {
  Text(text = "Hello}")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ChatAppTheme {
    }
}