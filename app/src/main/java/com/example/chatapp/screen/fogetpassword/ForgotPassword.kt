package com.example.chatapp.screen.fogetpassword

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.graphs.AuthScreen
import com.example.chatapp.utils.CustomImage
import com.example.chatapp.utils.ShowLoading
import com.example.chatapp.utils.ShowToast
import com.example.chatapp.utils.UserEmailField
import com.example.chatapp.utils.Result


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ForgotPasswordScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: ForgetPasswordViewModel?= hiltViewModel()
){
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF407BFF),
                title = { Text(text = "Rest Password",style = TextStyle(color =Color.White,fontSize = 16.sp)) },
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                tint = Color.White         ,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                }

            )
        },
        content = {
            ScreenContent(modifier = modifier, viewModel = viewModel!!)
        }
    )
    
     RestPasswordState(modifier,viewModel,navController)


}
@Composable
fun ScreenContent(modifier: Modifier,viewModel:ForgetPasswordViewModel){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {



        CustomImage(modifier, R.drawable.password,"forgot_password_image")
        UserEmailField(modifier =modifier ,
            emailInputField = viewModel.emailInput,
            onEmailChanged = viewModel::onEmailChange )
         SentRestEmailButton(modifier = modifier, viewModel = viewModel)


    }
}
@Composable
fun RestPasswordState(modifier: Modifier, viewModel: ForgetPasswordViewModel?, navController: NavHostController) {
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
                            navController.navigate(AuthScreen.Login.route)
                        }
                    }
                }

            }



        }




@Composable
fun SentRestEmailButton(modifier: Modifier, viewModel: ForgetPasswordViewModel?){
    val isEnable = viewModel?.isButtonEnable?.collectAsState()

    Button(onClick = {
                 viewModel?.forgotPassword()
    }, shape = RoundedCornerShape(16.dp),
        enabled = isEnable!!.value,
        modifier = modifier
            .width(240.dp)
            .padding(top = 16.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color(0xFF407BFF),
            contentColor = MaterialTheme.colors.onPrimary,

            )
    ) {

        Text(
            text = stringResource(id = R.string.send_email),
            style = TextStyle(color =Color.White, fontSize = 18.sp),
            modifier = modifier.padding(8.dp)
        )

    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 500)
@Composable
fun ForgotPasswordPreview(){
    ForgotPasswordScreen(Modifier, navController = rememberNavController(), viewModel = null)
}