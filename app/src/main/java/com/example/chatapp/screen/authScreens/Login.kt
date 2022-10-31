package com.example.chatapp.screen.authScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.sealedClasses.Screen
import com.google.firebase.auth.FirebaseUser

@Composable
fun LoginScreen(modifier: Modifier =Modifier,
                viewModel: AuthViewModel?, navController: NavHostController
) {

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }




            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
            ) {

               Image(modifier = modifier, image = R.drawable.register_image, des = "login_image")
                UserEmailField(
                    modifier = modifier,
                    viewModel = viewModel,
                    email = email,
                    onUseEmailChange = { email = it })
                passwordField(
                    modifier = modifier,
                    viewModel = viewModel,
                    password = password,
                    onPasswordChange = { password = it })
                ForgotPassword(modifier,navController)
                LoginButton(
                    modifier = modifier,
                    viewModel = viewModel,
                    email = email.text,
                    password = password.text
                )
                newUserRow(modifier = modifier, navController)

            }





    val authResource = viewModel?.loginFlow?.collectAsState()
    LoginStatus(modifier
        ,navController,authResource)


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

@Composable
fun LoginImage(modifier: Modifier) {

    Image(
        painter = painterResource(R.drawable.register_image),
        contentDescription = "login_image",
        modifier
            .width(200.dp)
            .height(200.dp), contentScale = ContentScale.Crop
    )

}

@Composable
fun LoginStatus(modifier: Modifier,navController: NavHostController,authResource: State<Result<FirebaseUser>?>?) {
    val context = LocalContext.current

    authResource?.value.let { result->
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
            }

        }



    }







@Composable
fun LoginButton(modifier: Modifier,viewModel: AuthViewModel?,email: String,password:String) {
    Button(onClick = {
          viewModel?.loginUser(email,password)
    }, shape = RoundedCornerShape(16.dp),
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