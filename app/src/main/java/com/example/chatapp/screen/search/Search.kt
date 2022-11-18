package com.example.chatapp.screen.search

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.model.User
import com.example.chatapp.utils.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun SearchScreen(modifier: Modifier,viewModel: SearchViewModel= hiltViewModel(),navController: NavHostController){
       Scaffold(
           topBar = {SearchTopBar(modifier,viewModel,navController)}
       ) {contentPadding->

           SearchBody(modifier, viewModel)

           }


    }

    @Composable
    fun SearchBody(modifier:Modifier,viewModel: SearchViewModel) {
        var users :List<User> = listOf()
        val searchState =  viewModel.searchResults.collectAsState()
        var isFailed by remember { mutableStateOf(false) }
         searchState.value.let { response ->
//             when(response){
//                 is com.example.chatapp.utils.Result.Resource1.Loading ->{
//                     ShowLoading(modifier=modifier)
//                 }
//                 is com.example.chatapp.utils.Result.Resource1.Failure ->{
//                   isFailed=true
//
//                 }
//                 is com.example.chatapp.utils.Result.Resource1.Success -> {
//                      users=response.result
//                 }
//         }
             Box (modifier.fillMaxSize()){
                 if (users.isNotEmpty()){
                  LazyColumn(){
                      items(users){user->

                          val isFriend = remember(user) { mutableStateOf(viewModel.isFriendOf(user)) }

                          SearchContent(
                              modifier,
                              user = user,
                              onSearchResultClicked ={} ,
                              onAddAsContactClicked = viewModel::onAddFriendClicked,
                              isFriend = isFriend ,
                              profilePicture =null
                          )
                      }
                  }
               }else{
                    if(isFailed){
                        Text( modifier = modifier.align(Alignment.Center),text = "No Result Found", style = MaterialTheme.typography.subtitle1)

                    }

               }

                 }
         }
    }



    @Composable
    fun SearchContent(
        modifier:Modifier,
        user: User,
        onSearchResultClicked: (String) -> Unit,
        onAddAsContactClicked: (String, MutableState<Boolean>) -> Unit,
        isFriend: MutableState<Boolean>,
        profilePicture: Bitmap?
    ) {
        Card(
            modifier.fillMaxWidth().padding(4.dp)
                .clickable { onSearchResultClicked.invoke(user.id) },
        )
        {


            Box(
                modifier = Modifier.fillMaxWidth()
            ) {


                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier
                            .padding(16.dp)
                            .clip(CircleShape)
                    ) {
                        if(profilePicture!=null) {
                            ProfilePicture(picture = profilePicture, name = user.name)
                        } else {
                            DefaultProfilePicture(modifier, name = user.name)
                        }
                    }

                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                when {
                    isFriend.value -> {
                        Log.e(TAG, "SearchContent: add to friend ",)

                    }
                    else -> {

                        IconButton(
                            onClick = { onAddAsContactClicked.invoke(user.id, isFriend) },
                            modifier.align(Alignment.CenterEnd)
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = stringResource(id = R.string.add_friend_description)
                            )
                        }

                    }
                }
            }
        }

    }





    @Composable
    fun SearchTopBar(modifier: Modifier,viewModel: SearchViewModel,navController: NavHostController) {
        TopAppBar(
            backgroundColor = Color(0xFF407BFF),
            navigationIcon = {
                IconButton(onClick = { onBackClicked(navController =navController ) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                         tint = Color.White,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            },
            title = { SearchBar(modifier,viewModel,viewModel::onSearchValueChange)}
        )
    }
    @Composable
    fun SearchBar(
        modifier: Modifier,
        viewModel: SearchViewModel,
        onSearchBarValueChanged: (String) -> Unit

    ){
        val search = viewModel.searchBar.collectAsState().value

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            color = Color(0xFF407BFF)
        ) {
            TextField(modifier = Modifier
                .fillMaxWidth(),
                value = search.input,
                onValueChange = {
                    onSearchBarValueChanged(it)
                },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        text = "Search here...",
                        color = Color.White
                    )
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.subtitle1.fontSize, color = Color.White
                ),
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.White
                        )
                    }
                },

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {

                    }
                ),

                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
                ))
        }

    }


    @Preview
    @Composable
    fun SearchPreview(){
       // SearchScreen(Modifier)

    }