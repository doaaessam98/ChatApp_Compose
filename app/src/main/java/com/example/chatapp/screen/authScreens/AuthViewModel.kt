package com.example.chatapp.screen.authScreens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.R
import com.example.chatapp.data.repository.IRepository
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.isEmail
import com.example.chatapp.utils.isUserName
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: IRepository,
                                          application: Application
                                        ) :AndroidViewModel(application){

    private val _loginFlow = MutableStateFlow<Result<FirebaseUser>?> (Result.Idle)

    val loginFlow :StateFlow<Result<FirebaseUser>?> = _loginFlow


    private val _signupFlow = MutableStateFlow<Result<FirebaseUser>?> (Result.Idle)
    val signupFlow :StateFlow<Result<FirebaseUser>?> = _signupFlow


    private val _restPasswordFlow = MutableStateFlow<Result<Boolean>?> (Result.Idle)

    val restPasswordFlow:StateFlow<Result<Boolean>?> = _restPasswordFlow

     var userNameError = MutableStateFlow("")

     val emailError = MutableStateFlow ("")

    val passwordError = MutableStateFlow("")
   private var requiredError= getApplication<Application>().getString(R.string.reqired)





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
                    _loginFlow.emit(Result.Loading)
                    val result = repository.login( email, password)
                    _loginFlow.emit(result)


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
                    _signupFlow.emit(Result.Loading)
                    val result = repository.signup(name, email, password)
                    _signupFlow.emit(result)
                    delay(300)
                    _signupFlow.emit(Result.Idle)

                }
            }
        }
    }


  fun forgotPassword(email: String){
      when {


          email.isEmpty() -> {
              emailError.value = requiredError

          }
          !email.isEmail() -> {
              emailError.value = getApplication<Application>().getString(R.string.enter_user_name)
          }
          else -> {
              viewModelScope.launch {
                  _restPasswordFlow.emit(Result.Loading)
                  val result = repository.forgotPassword(email)
                  _restPasswordFlow.emit(result)
                  delay(300)
                  _restPasswordFlow.emit(Result.Idle)
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