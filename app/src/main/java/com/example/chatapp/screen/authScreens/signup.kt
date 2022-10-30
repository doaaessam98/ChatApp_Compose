package com.example.chatapp.screen.authScreens.register

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.screen.authScreens.*
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.Screen
import com.google.firebase.auth.FirebaseUser


@Composable
fun SignupScreen(
    modifier: Modifier =Modifier,
    viewModel: AuthViewModel?, navController: NavHostController
){
    var userName by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember{ mutableStateOf(TextFieldValue("")) }
    var password by remember{ mutableStateOf(TextFieldValue("")) }



    Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top, modifier = modifier
                .fillMaxWidth())
    {

        Image(modifier = modifier, des = "register_image", image =R.drawable.register_image )

              UserNameField(modifier,  viewModel = viewModel,userName = userName, onUseNameChange = { userName=it })

              UserEmailField(modifier = modifier,viewModel = viewModel,email = email, onUseEmailChange ={ email=it})

              passwordField(modifier = modifier,viewModel = viewModel,password =password, onPasswordChange = {password=it})

              RegisterButton(modifier = modifier, viewModel = viewModel, userName =userName.text , email =email.text , password =password.text )

              HaveAccountRow(modifier, navController = navController)



    }
    val authResource = viewModel?.signupFlow?.collectAsState()
    signupStatus(modifier,authResource,navController)

    
}
@Composable
fun signupStatus(modifier: Modifier,authResource: State<Result<FirebaseUser>?>?,navController: NavHostController) {
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

       }



   }



}




@Composable
fun UserNameField(
    modifier: Modifier,
    viewModel: AuthViewModel?,
    userName: TextFieldValue,
    onUseNameChange: (TextFieldValue) -> Unit,


) {
    val focusManager = LocalFocusManager.current
    val error =viewModel?.userNameError?.collectAsState()?.value

    Column() {

        OutlinedTextField(
            value = userName,
            onValueChange = { onUseNameChange(it)
                             viewModel?.userNameError?.value=""},
            label = { Text(text = stringResource(id = R.string.enter_user_name)) },
            maxLines = 1,
            textStyle = TextStyle(),
            leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
            isError=error!!.isNotEmpty(),
            keyboardActions = KeyboardActions(onNext = {
              focusManager.moveFocus(FocusDirection.Down) }),
            modifier = modifier.padding(top = 16.dp)


        )
        if (error.isNotEmpty()){
            Text(text =error ,
            style = TextStyle(color = MaterialTheme.colors.error)
            )
        }
    }
}




@Composable
fun RegisterButton(modifier: Modifier,viewModel: AuthViewModel?,
                   userName: String, email : String,password: String) {
    Button(onClick = {
        viewModel?.signupUser(userName,email,password)
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

