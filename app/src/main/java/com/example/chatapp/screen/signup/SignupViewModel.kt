package com.example.chatapp.screen.signup

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.R
import com.example.chatapp.data.repository.IRepository
import com.example.chatapp.model.InputField
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.isEmail
import com.example.chatapp.utils.isUserName
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: IRepository,
      application: Application
     ) :AndroidViewModel(application){



    private val _emailInput = MutableStateFlow(InputField())
    val emailInput: StateFlow<InputField> = _emailInput

    private val _passwordInput = MutableStateFlow(InputField())
    val passwordInput: StateFlow<InputField> = _passwordInput

    private val _nameInput = MutableStateFlow(InputField())
    val nameInput: StateFlow<InputField> = _nameInput


    private val _signupFlow = MutableStateFlow<Result<FirebaseUser>?> (Result.Idle)
    val signupFlow :StateFlow<Result<FirebaseUser>?> = _signupFlow


    private val _isButtonEnable = MutableStateFlow(true)
    val isButtonEnable: StateFlow<Boolean> = _isButtonEnable


   private var requiredError= getApplication<Application>().getString(R.string.reqired)

    fun signupUser() {
        val  email =emailInput.value.input
        val password =passwordInput.value.input
        val name=nameInput.value.input

        when {
            name.isEmpty() -> {
                _nameInput.value=nameInput.value.copy(isError = true, errorMessage = requiredError)
            }

            !name.isUserName()->{
                val error=getApplication<Application>().getString(R.string.invald_user_name)
                _nameInput.value=nameInput.value.copy(isError = true, errorMessage = error)
            }

            email.isEmpty() -> {
                _emailInput.value=emailInput.value.copy(isError = true,errorMessage = requiredError)

            }

            !email.isEmail() -> {
                val error =getApplication<Application>().getString(R.string.invalid_email)
                _emailInput.value =emailInput.value.copy(isError=true,errorMessage = error)

            }

            password.isEmpty() -> {
                _passwordInput.value=emailInput.value.copy(isError = true,errorMessage = requiredError)
            }

//            !password.isPassword()->{
//              val error =context.getString(R.string.invalid_password)
            //          _passwordInput.value =emailInput.value.copy(isError=true,errorMessage = error)
            //   }

            else -> {
                viewModelScope.launch {
                    _isButtonEnable.emit(false)
                    _signupFlow.emit(Result.Loading)
                    val result = repository.signup(name, email, password)
                    _signupFlow.emit(result)
                    _isButtonEnable.emit(true)
                    delay(300)
                    _signupFlow.emit(Result.Idle)

                }
            }
        }
    }




  fun onEmailChange(newEmailValue: String){
      if(newEmailValue.isBlank()) {

          _emailInput.value = emailInput.value.copy(input = newEmailValue, isError = true, errorMessage = requiredError)
      } else {
          _emailInput.value = _emailInput.value.copy(input = newEmailValue, isError = false)
      }
  }

    fun onNameChanged(newNameValue: String) {
        if(newNameValue.isBlank()) {
            _nameInput.value = nameInput.value.copy(input = newNameValue, isError = true, errorMessage = requiredError)
        } else {
            _nameInput.value = _nameInput.value.copy(input = newNameValue, isError = false)


    }}


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
                _passwordInput.value = _passwordInput.value.copy(input = newPasswordValue, isError = false)
            }
        }

    }








}