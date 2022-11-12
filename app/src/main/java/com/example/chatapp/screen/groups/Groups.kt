package com.example.chatapp.screen.groups

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.graphs.TopBarScreen
import com.example.chatapp.utils.FloatingButton

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GroupsScreen(modifier: Modifier,navController: NavHostController){
    Scaffold(
        floatingActionButton = {
            FloatingButton(modifier, Icons.Default.GroupAdd) {
            navController.navigate(TopBarScreen.Search.route)
        }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddintContent->

    }

}
