package com.example.chatapp.screen.authScreens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.chatapp.R

@Composable
fun Image(modifier: Modifier,des:String,image:Int) {
    androidx.compose.foundation.Image(
        painter = painterResource(image),
        contentDescription = des,
        modifier
            .width(200.dp)
            .height(200.dp), contentScale = ContentScale.Crop
    )

}
@Composable
fun UserEmailField(
    modifier: Modifier,
    email: TextFieldValue,
    viewModel: AuthViewModel?,
    onUseEmailChange: (TextFieldValue) -> Unit,


    ) {
    val focusManager = LocalFocusManager.current
    val error =viewModel?.emailError?.collectAsState()?.value



    Column() {


        OutlinedTextField(
            value = email,
            onValueChange = {
                viewModel?.emailError?.value=""
                onUseEmailChange(it)
            },
            label = { Text(text = stringResource(id = R.string.enter_email)) },
            maxLines = 1,
            textStyle = TextStyle(),
            leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = "") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Email),
            isError= error!!.isNotEmpty(),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down) }),
            modifier = modifier.padding(top = 16.dp)


        )
    }
    if (error!!.isNotEmpty()) {
        Text(text = error,
            style = TextStyle(color = MaterialTheme.colors.error)
        )

    }

}
@Composable
fun passwordField(
    modifier: Modifier,
    password: TextFieldValue,
    viewModel:AuthViewModel?,
    onPasswordChange: (TextFieldValue) -> Unit,


    ) {
    val focusManager = LocalFocusManager.current
    val error =viewModel?.passwordError?.collectAsState()?.value


    var passwordVisible by remember { mutableStateOf(false) }
    val image = if (passwordVisible)
        Icons.Filled.Visibility
    else Icons.Filled.VisibilityOff
    val description = if (passwordVisible) "Hide password" else "Show password"

    Column() {

        OutlinedTextField(

            value = password,
            onValueChange = { onPasswordChange(it)
                viewModel?.passwordError?.value=""},
            label = { Text(text = stringResource(id = R.string.enter_password)) },
            singleLine = true,
            textStyle = TextStyle(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }

            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isError= error!!.isNotEmpty(),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = modifier.padding(top = 16.dp),



        )
        if (error.isNotEmpty()){
            Text(text = error,
                style = TextStyle(color = MaterialTheme.colors.error)
            )
        }
    }
}


@Composable
fun ShowToast(context: Context, message:String){
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}

@Composable
fun ShowLoading(modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color.Gray,
        )
    }
}

@Composable
private fun CircularProgressAnimated(){
    val progressValue = 0.75f
    val infiniteTransition = rememberInfiniteTransition()

    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = progressValue,animationSpec = infiniteRepeatable(animation = tween(900)))

    CircularProgressIndicator(progress = progressAnimationValue)
}


@Composable
fun PhoneNumberField(
    modifier: Modifier,
    phoneNumber:String,
    onPhoneChange: (String) -> Unit,
    error:String

) {
    Column() {
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { onPhoneChange(it) },
            label = { Text(text = stringResource(id = R.string.enter_user_phone)) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Call, contentDescription = "") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Phone
            ),
            keyboardActions = KeyboardActions(
                onSend = { }),
            modifier = modifier.padding(top = 16.dp)

        )
        Text(
            text =error,
            style = TextStyle(color = MaterialTheme.colors.error)

        )
    }
}