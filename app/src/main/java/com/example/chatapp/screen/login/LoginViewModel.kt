package com.example.chatapp.screen.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.R
import com.example.chatapp.data.repository.IRepository
import com.example.chatapp.model.InputField
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.isEmail
import com.example.chatapp.utils.isPassword
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IRepository,
    @ApplicationContext private val context: Context
) :ViewModel(){


    private val _loginFlow = MutableStateFlow<Result<FirebaseUser>?> (Result.Idle)

    val loginFlow : StateFlow<Result<FirebaseUser>?> = _loginFlow

    private val _emailInput = MutableStateFlow(InputField())
    val emailInput: StateFlow<InputField> = _emailInput

    private val _passwordInput = MutableStateFlow(InputField())
    val passwordInput: StateFlow<InputField> = _passwordInput

    private var requiredError= context.getString(R.string.reqired)

    private val _isButtonEnable = MutableStateFlow(true)
    val isButtonEnable: StateFlow<Boolean> = _isButtonEnable


    val currentUser: FirebaseUser?
        get() = repository.currentUser


    @SuppressLint("SuspiciousIndentation")
    fun loginUser() {

        val email=emailInput.value.input
        val  password=passwordInput.value.input
            when {

                email.isEmpty() -> {
                    _emailInput.value=emailInput.value.copy(isError = true,errorMessage = requiredError)

                }

                !email.isEmail() -> {
                    val error =context.getString(R.string.invalid_email)
                    _emailInput.value =emailInput.value.copy(isError=true,errorMessage = error)

                }

                password.isEmpty() -> {
                    _passwordInput.value=emailInput.value.copy(isError = true,errorMessage = requiredError)
                }

//            !password.isPassword()->{
//              val error =context.getString(R.string.invalid_password)
//                _passwordInput.value =emailInput.value.copy(isError=true,errorMessage = error)
//           }

                else -> {
                    _isButtonEnable.value=false
                    viewModelScope.launch {
                        _loginFlow.emit(Result.Loading)
                        val result = repository.login(email, password)
                        _loginFlow.emit(result)
                        _isButtonEnable.emit(true)
                        delay(300)
                        _loginFlow.emit(Result.Idle)
                    }
                }

        }
    }

    fun onEmailChange(newEmailValue: String){
        if(newEmailValue.isBlank()) {
            val message = requiredError
            _emailInput.value = emailInput.value.copy(input = newEmailValue, isError = true, errorMessage = message)
        } else {
            _emailInput.value = _emailInput.value.copy(input = newEmailValue, isError = false)
        }
    }
    fun onPasswordChanged(newPasswordValue: String) {
        when {
            newPasswordValue.isBlank() -> {

                _passwordInput.value = passwordInput.value.copy(input = newPasswordValue, isError = true, errorMessage = requiredError)
            }
//            !newPasswordValue.isPassword()->{
//                val error =context.getString(R.string.invalid_password)
//                          _passwordInput.value =emailInput.value.copy(isError=true,errorMessage = error)
//           }
            else -> {
                _passwordInput.value = passwordInput.value.copy(input = newPasswordValue, isError = false)
            }
        }

    }


}