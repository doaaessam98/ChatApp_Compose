package com.example.chatapp.screen.fogetpassword

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.R
import com.example.chatapp.data.repository.userRepository.IRepository
import com.example.chatapp.model.ForgotPasswordScreenState
import com.example.chatapp.model.InputField
import com.example.chatapp.utils.Resource
import com.example.chatapp.utils.isEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val repository: IRepository,
    application: Application
) : AndroidViewModel(application){

    private val _emailInput = MutableStateFlow(InputField())
    val emailInput: StateFlow<InputField> = _emailInput

    private var requiredError= getApplication<Application>().getString(R.string.reqired)

    private val _restPasswordFlow = MutableStateFlow (ForgotPasswordScreenState())

    val restPasswordFlow:StateFlow<ForgotPasswordScreenState> = _restPasswordFlow

    private val _isButtonEnable = MutableStateFlow(true)
    val isButtonEnable: StateFlow<Boolean> = _isButtonEnable

    fun forgotPassword(){
        val email = emailInput.value.input
        when {


            email.isEmpty() -> {
                _emailInput.value=emailInput.value.copy(isError = true,errorMessage = requiredError)

            }

            !email.isEmail() -> {
                val error =getApplication<Application>().getString(R.string.invalid_email)
                _emailInput.value =emailInput.value.copy(isError=true,errorMessage = error)

            }
            else -> {
                _isButtonEnable.value=false
                viewModelScope.launch {
                    val response = repository.forgotPassword(email)

                    when (response) {
                        is Resource.Loading -> {
                            _restPasswordFlow.value =ForgotPasswordScreenState(isLoading = true)
                        }
                        is Resource.Error -> {
                            _restPasswordFlow.value =
                                ForgotPasswordScreenState(
                                    hasError = true,
                                    errorMessage = response.message
                                )
                        }
                        is Resource.Success -> {
                            Log.e(ContentValues.TAG, "GroupsScreenContent: ", )
                            _restPasswordFlow.value= ForgotPasswordScreenState(

                            )
                        }


                }
            }
        }
    }}
    fun onEmailChange(newEmailValue: String){
        if(newEmailValue.isBlank()) {
            val message = requiredError
            _emailInput.value = emailInput.value.copy(input = newEmailValue, isError = true, errorMessage = message)
        } else {
            _emailInput.value = _emailInput.value.copy(input = newEmailValue, isError = false)
        }
    }
}