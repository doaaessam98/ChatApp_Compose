package com.example.chatapp.screen.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.screen.login.LoginViewModel
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.sealedClasses.Screen

@Composable
fun LoginScreen(modifier: Modifier =Modifier,
                viewModel: LoginViewModel?=hiltViewModel(), navController: NavHostController
) {






            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
            ) {

               Image(modifier = modifier, image = R.drawable.login, des = "login_image")
                UserEmailField(
                    modifier = modifier,
                    emailInputField = viewModel!!.emailInput,
                    onEmailChanged = viewModel::onEmailChange

                    )
                passwordField(
                    modifier = modifier,
                    passwordInputField = viewModel.passwordInput,
                    onPasswordChanged = viewModel::onPasswordChanged
                   )
                ForgotPassword(modifier,navController)
                LoginButton(
                    modifier = modifier,
                    viewModel = viewModel,
                )
                newUserRow(modifier = modifier, navController)

            }






    LoginStatus(modifier
        ,navController,viewModel)


}

@Composable
fun ForgotPassword(modifier: Modifier,navController: NavHostController) {
    ClickableText(text = AnnotatedString(stringResource(id = R.string.forgot_password)),
        modifier = modifier.padding(start = 167.dp),
        style = TextStyle(color =Color(0xFF407BFF), textDecoration = TextDecoration.Underline),
        onClick ={
              navController.navigate(Screen.ForgotPassword.route)
    } )
}



@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginStatus(modifier: Modifier,navController: NavHostController,viewModel: LoginViewModel?) {
    val context = LocalContext.current

 val state= viewModel?.loginFlow?.collectAsState()
     state?.value.let { result->
        when(result){
            is Result.Loading ->{

            ShowLoading(modifier = modifier)
            }
            is Result.Failure ->{
                ShowToast(context,message = result.exception.message.toString())

            }
            is Result.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
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
fun LoginButton(modifier: Modifier,viewModel: LoginViewModel?) {
    val isEnable = viewModel?.isButtonEnable?.collectAsState()

    Button(onClick = {
          viewModel?.loginUser()
    },
        enabled = isEnable!!.value,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .width(240.dp)
            .padding(top = 16.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor =Color(0xFF407BFF),
            contentColor =MaterialTheme.colors.onPrimary,

            )
    ) {

        Text(text = stringResource(id = R.string.login),
            modifier=modifier.padding(8.dp))

    }
}
@Composable
fun newUserRow(modifier: Modifier,navController: NavHostController) {
    Row(modifier = modifier.padding(top = 16.dp)) {
        Text(text = stringResource(id = R.string.have_an_account) )
        ClickableText(text = AnnotatedString(stringResource(id = R.string.register)),
            style = TextStyle(color =Color(0xFF407BFF))  ,
            onClick = {
                navController.navigate(Screen.Signup.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })

    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 500)
@Composable
fun  LoginPreview(){
    ChatAppTheme {
        LoginScreen(Modifier,null, rememberNavController())

    }
}