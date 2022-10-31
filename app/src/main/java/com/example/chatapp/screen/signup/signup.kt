package com.example.chatapp.screen.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.model.InputField
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.sealedClasses.Screen
import kotlinx.coroutines.flow.StateFlow


@Composable
fun SignupScreen(
    modifier: Modifier =Modifier,
    viewModel: SignupViewModel?= hiltViewModel(), navController: NavHostController
){




    Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top, modifier = modifier
                .fillMaxWidth())
    {


             SignupImage(modifier = modifier)
              UserNameField(modifier,
                  nameInputField = viewModel!!.nameInput,
                  onNameChanged = viewModel::onNameChanged
              )

              UserEmailField(modifier = modifier,
                  emailInputField = viewModel.emailInput,
                  onEmailChanged = viewModel::onEmailChange
              )

              passwordField(modifier = modifier,
              passwordInputField = viewModel.passwordInput,
              onPasswordChanged = viewModel::onPasswordChanged
              )

              RegisterButton(modifier = modifier, viewModel = viewModel )

              HaveAccountRow(modifier, navController = navController)



    }

    SignupStatus(modifier,viewModel,navController)

    
}
@Composable
fun SignupStatus(modifier: Modifier, viewModel:SignupViewModel?, navController: NavHostController) {
    val authResource = viewModel?.signupFlow?.collectAsState()


    val context = LocalContext.current
    authResource?.value.let { result->
       when(result){
            is Result.Loading ->{
                ShowLoading(modifier=modifier)
            }
           is Result.Failure ->{
               ShowToast(context,message = result.exception.message.toString())
           }
           is Result.Success -> {
               LaunchedEffect(Unit) {
                   navController.navigate(Screen.Home.route) {
                       popUpTo(Screen.Signup.route) {
                           inclusive = true
                       }
                   }
               }
           }

           else -> {}
       }



   }



}


@Composable
fun SignupImage(modifier: Modifier) {

    androidx.compose.foundation.Image(
        painter = painterResource(R.drawable.register_image),
        contentDescription = "register_image",
        modifier
            .width(200.dp)
            .height(200.dp), contentScale = ContentScale.Crop
    )

}

@Composable
fun UserNameField(
    modifier: Modifier,
    nameInputField: StateFlow<InputField>,
    onNameChanged: (String) -> Unit,


    ) {
    val focusManager = LocalFocusManager.current
    val name =nameInputField.collectAsState().value

    Column {

        OutlinedTextField(
            value = name.input,
            onValueChange = { onNameChanged(it)},
            label = { Text(text = stringResource(id = R.string.enter_user_name)) },
            maxLines = 1,
            textStyle = TextStyle(),
            leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
            isError=name.isError,
            keyboardActions = KeyboardActions(onNext = {
              focusManager.moveFocus(FocusDirection.Down) }),
            modifier = modifier.padding(top = 16.dp)


        )
        if (name.isError){
            Text(text =name.errorMessage,
            style = TextStyle(color = MaterialTheme.colors.error)
            )
        }
    }
}




@Composable
fun RegisterButton(modifier: Modifier, viewModel: SignupViewModel?) {
    Button(onClick = {
        viewModel?.signupUser()
    }, shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .width(240.dp)
            .padding(top = 16.dp),
         colors = ButtonDefaults.textButtonColors(
             backgroundColor =Color(0xFF407BFF),
             contentColor = Color.White,

        )
    ) {

        Text(text = stringResource(id = R.string.register),modifier=modifier.padding(8.dp))

    }
}
@Composable
fun HaveAccountRow(modifier: Modifier,navController: NavHostController) {
    Row(modifier = modifier.padding(top = 16.dp)) {
        Text(text = stringResource(id = R.string.have_an_account) )
        ClickableText(text = AnnotatedString(stringResource(id = R.string.login)),
            style = TextStyle(color =Color(0xFF407BFF))  , onClick = {
                navController.navigate(Screen.Login.route){
                    popUpTo(Screen.Login.route){
                        inclusive=true
                    }
                }

            })

    }
}



@Preview(showBackground = true, widthDp = 420, heightDp = 600)
@Composable
fun RegisterScreenPreview(){
    ChatAppTheme {
        SignupScreen(Modifier,null, rememberNavController())
        
    }
}
