package com.example.chatapp.screen.authScreens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.R
import com.example.chatapp.data.repository.IRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.isEmail
import com.example.chatapp.utils.isPassword
import com.example.chatapp.utils.isUserName
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: IRepository,
                                          application: Application
                                        ) :AndroidViewModel(application){

    private val _loginFlow = MutableStateFlow<Result<FirebaseUser>?> (null)
    val loginFlow :StateFlow<Result<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Result<FirebaseUser>?> (null)
    val signupFlow :StateFlow<Result<FirebaseUser>?> = _signupFlow

     var userNameError = MutableStateFlow("")

     val emailError = MutableStateFlow ("")

    val passwordError = MutableStateFlow("")
   var requiredError= getApplication<Application>().getString(R.string.reqired)





    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            _loginFlow.value =Result.Success(repository.currentUser!!)
        }
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        when {

            email.isEmpty() -> {
                emailError.value = requiredError

            }

            !email.isEmail()->{
                emailError.value = getApplication<Application>().getString(R.string.invalid_email)
            }

            password.isEmpty() -> {
                passwordError.value = requiredError
            }

//            !password.isPassword()->{
//                passwordError.value = getApplication<Application>().getString(R.string.invalid_password)
//            }

            else -> {
                viewModelScope.launch {
                    _loginFlow.value = Result.Loading
                    val result = repository.login( email, password)
                    _loginFlow.value = result

                }
            }
        }
    }

    fun signupUser(name: String, email: String, password: String) {

        when {
            name.isEmpty() -> {
                userNameError.value=requiredError
            }

            !name.isUserName()->{
                userNameError.value=getApplication<Application>().getString(R.string.invald_user_name)
            }

            email.isEmpty() -> {
                emailError.value = requiredError

            }
            !email.isEmail()->{
                emailError.value = getApplication<Application>().getString(R.string.enter_user_name)
            }

            password.isEmpty() -> {
              passwordError.value = requiredError
            }

//            !password.isPassword()->{
//                passwordError.value = getApplication<Application>().getString(R.string.invalid_password)
//            }
            else -> {
                viewModelScope.launch {
                    _signupFlow.value = Result.Loading
                    val result = repository.signup(name, email, password)
                    _signupFlow.value = result

                }
            }
        }
    }



    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }





}