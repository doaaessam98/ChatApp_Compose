package com.example.chatapp.screen.authScreens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.sealedClasses.Screen
import com.google.firebase.auth.FirebaseUser


@Composable
fun ForgotPasswordScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: AuthViewModel?
){
   var email by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
       Image(modifier, R.drawable.register_image,"forgot_password_image")
        UserEmailField(modifier =modifier ,
            email = email, viewModel = viewModel,
            onUseEmailChange ={ email = it} )
        SentRestEmailButton(modifier = modifier, viewModel = viewModel,email.text)

        
    }
     restPasswordState(modifier,viewModel,navController)


}
@Composable
fun restPasswordState(modifier: Modifier, viewModel: AuthViewModel?,navController: NavHostController) {
    val context = LocalContext.current

    val state =viewModel?.restPasswordFlow?.collectAsState()
    state?.value.let {result->

                when(result){
                    is Result.Loading ->{

                        ShowLoading(modifier = modifier)
                    }
                    is Result.Failure ->{
                        ShowToast(context,message = result.exception.message.toString())

                    }
                    is Result.Success -> {
                        Log.e(TAG, "restPasswordState: Success${result.result}", )
                        LaunchedEffect(Unit) {
                            navController.popBackStack()
                            navController.navigate(Screen.Login.route)
                        }
                    }
                }

            }



        }




@Composable
fun SentRestEmailButton(modifier: Modifier,viewModel: AuthViewModel?,email:String){
    Button(onClick = {
                 viewModel?.forgotPassword(email)
    }, shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .width(240.dp)
            .padding(top = 16.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color(0xFF407BFF),
            contentColor = MaterialTheme.colors.onPrimary,

            )
    ) {

        androidx.compose.material.Text(
            text = stringResource(id = R.string.send_email),
            modifier = modifier.padding(8.dp)
        )

    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 500)
@Composable
fun ForgotPasswordPreview(){
    ForgotPasswordScreen(Modifier, navController = rememberNavController(), viewModel = null)
}