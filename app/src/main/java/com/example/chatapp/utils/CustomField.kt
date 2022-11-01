package com.example.chatapp.utils

import android.content.Context
import android.content.res.Resources
import android.media.Image
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.model.InputField
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CustomImage(modifier: Modifier, image:Int, des:String) {


   Image(
       painter = painterResource(image),
        contentDescription = des,
        modifier
            .width(200.dp)
            .height(200.dp),
        contentScale = ContentScale.Crop
    )
}

private fun loadImageBitmapResource(res: Resources, id: Int): ImageBitmap {
    try {
        return ImageBitmap.imageResource(res, id)
    } catch (throwable: Throwable) {
        throw IllegalArgumentException(throwable.message)
    }
}
@Composable
fun UserEmailField(
    modifier: Modifier,
    emailInputField: StateFlow<InputField>,
    onEmailChanged: (String) -> Unit,



    ) {
    val focusManager = LocalFocusManager.current
    val email =emailInputField.collectAsState()




    Column() {

        OutlinedTextField(
            value = email.value.input,
            onValueChange = {
             onEmailChanged(it)
            },
            label = { Text(text =if(email.value.isError) stringResource(id = R.string.email_example) else stringResource(id = R.string.enter_email)) },
            maxLines = 1,
            textStyle = TextStyle(),
            leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = "") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Email),
            isError= email.value.isError,

            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down) }),
            modifier = modifier.padding(top = 16.dp)


        )
    }
    if (email.value.isError) {
        Text(text = email.value.errorMessage,
            style = TextStyle(color = MaterialTheme.colors.error)
        )

    }

}
@Composable
fun passwordField(
    modifier: Modifier,
    passwordInputField: StateFlow<InputField>,
    onPasswordChanged: (String) -> Unit,


    ) {
    val focusManager = LocalFocusManager.current
    val password =passwordInputField.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    val image = if (passwordVisible)
        Icons.Filled.Visibility
    else Icons.Filled.VisibilityOff
    val description = if (passwordVisible) "Hide password" else "Show password"

    Column() {

        OutlinedTextField(

            value = password.value.input,
            onValueChange = { onPasswordChanged(it)},
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
            isError= password.value.isError,
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = modifier.padding(top = 16.dp),



        )
        if (password.value.isError){
            Text(text = password.value.errorMessage,
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
            modifier = Modifier.size(64.dp),
            color = Color(0xFF407BFF),
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
            label = { Text(text =stringResource(id = R.string.enter_user_phone)) },
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