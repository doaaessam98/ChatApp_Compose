package com.example.chatapp.screen.fogetpassword

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.R
import com.example.chatapp.data.repository.IRepository
import com.example.chatapp.model.InputField
import com.example.chatapp.utils.Result
import com.example.chatapp.utils.isEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private val _restPasswordFlow = MutableStateFlow<Result<Boolean>?> (Result.Idle)

    val restPasswordFlow:StateFlow<Result<Boolean>?> = _restPasswordFlow

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
    fun onEmailChange(newEmailValue: String){
        if(newEmailValue.isBlank()) {
            val message = requiredError
            _emailInput.value = emailInput.value.copy(input = newEmailValue, isError = true, errorMessage = message)
        } else {
            _emailInput.value = _emailInput.value.copy(input = newEmailValue, isError = false)
        }
    }
}