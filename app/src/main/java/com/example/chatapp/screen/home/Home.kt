package com.example.chatapp.screen.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.graphs.HomeNavGraph
import com.example.chatapp.graphs.HomeNavigationItem
import com.example.chatapp.graphs.TopBarScreen
import com.example.chatapp.ui.theme.ChatAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()

) {
    var isTopBarVisible by remember { mutableStateOf(true) }

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,

        topBar = { if(isTopBarVisible) HomeTopBar(modifier = modifier, navController) },
        bottomBar = { HomeBottomBarNavigation(modifier, navController, setTopBar = {isTopBarVisible=it}) }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
          ) {
        HomeNavGraph(modifier = modifier, navController = navController)
        }

    }
}

fun onSearchClicked(navController: NavHostController){
   navController.navigate(TopBarScreen.Search.route){

   }
}

fun onProfileClicked(navController: NavHostController) {
       navController.navigate(TopBarScreen.Profile.route)
}

@Composable
fun HomeTopBar(modifier: Modifier,navController: NavHostController) {
    TopAppBar(
        contentColor = Color.White,
        backgroundColor = Color(0xFF407BFF),
     title = { Text(text = stringResource(id =R.string.app_name))},

     actions = {
            IconButton(onClick = { onSearchClicked(navController) }) {
                Icon(
                    imageVector = Icons.Default.Search,

                    contentDescription =TopBarScreen.Search.route
                )
            }
            IconButton(onClick = { onProfileClicked(navController) }) {
                Icon(imageVector = Icons.Rounded.Person
                , contentDescription =TopBarScreen.Profile.route)

            }
        },

    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeBottomBarNavigation(modifier: Modifier, navController: NavHostController, setTopBar:(Boolean)->Unit) {
    val navItems = listOf(
        HomeNavigationItem.Chats,
        HomeNavigationItem.Groups,
        HomeNavigationItem.Friends
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val bottomBarDestination = navItems.any { it.route == currentDestination }
    if (bottomBarDestination) {
        setTopBar(true)
        BottomNavigation(modifier.fillMaxWidth(), backgroundColor = Color.White) {
           navItems.forEach { screen ->

                AddItem(screen = screen,
                    currentDestination = currentDestination,
                    navController = navController)



           }
    }
    }else{
        Log.e(TAG, "HomeBottomBarNavigation: ", )
        setTopBar(false)
    }
        

}
@Composable
fun RowScope.AddItem(
    screen: HomeNavigationItem,
    currentDestination: String?,
    navController: NavHostController
) {

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.icon),
                        contentDescription = stringResource(id = screen.title)
                    )
                },

                label = { Text(text = stringResource(id = screen.title)) },

                alwaysShowLabel = true,
                /**
                 * dont work in dark mode
                 * to support dark mode should use by default
                 * unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                 *
                 */
                selected = currentDestination == screen.route,
                selectedContentColor = Color(0xFF407BFF),
                unselectedContentColor = Color.Black,

                onClick = {
                    navController.navigate(screen.route) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true

                    }
                }


            )
        }


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ChatAppTheme {
        HomeScreen(navController = rememberNavController(),)
    }
}