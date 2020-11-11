package masterchefdevs.colectiv.ubb.chefs.auth.login

import android.content.ContentValues.TAG
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.auth.data.LoginRepository
import masterchefdevs.colectiv.ubb.chefs.auth.data.TokenHolder
import masterchefdevs.colectiv.ubb.chefs.core.Result

class LoginViewModel : ViewModel() {

    private val mutableLoginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = mutableLoginFormState

    private val mutableLoginResult = MutableLiveData<Result<TokenHolder>>()
    val loginResult: LiveData<Result<TokenHolder>> = mutableLoginResult

    fun login(username: String, password: String) {
        Log.v(TAG, "inside view model")
        viewModelScope.launch {
            Log.v(TAG, "login...");
            mutableLoginResult.value = LoginRepository.login(username, password)
        }
    }
    fun register(username: String, password1: String, password2: String) {
        Log.v(TAG, "inside view model register")
        viewModelScope.launch {
            Log.v(TAG, "register...");
            mutableLoginResult.value = LoginRepository.register(username, password1, password2)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        Log.v(TAG, "inside loginvoewModel")
        if (!isUserNameValid(username)) {
            mutableLoginFormState.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            mutableLoginFormState.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            mutableLoginFormState.value = LoginFormState(isDataValid = true)
        }
    }
    fun loginDataChanged(username: String, password: String, password2: String) {
        Log.v(TAG, "inside loginvoewModel")
        if (!isUserNameValid(username)) {
            mutableLoginFormState.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            mutableLoginFormState.value = LoginFormState(passwordError = R.string.invalid_password)
        } else if (!isPassword2Valid(password, password2)) {
            mutableLoginFormState.value =
                LoginFormState(passwordError = R.string.invalid_password_confirmation)
        }else{
            mutableLoginFormState.value = LoginFormState(isDataValid = true)
        }
    }


    private fun isUserNameValid(username: String): Boolean {
        Log.v(TAG, "inside is username valid")
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 1
    }
    private fun isPassword2Valid(password1: String, password2: String): Boolean {
        return password1.compareTo(password2)==0
    }
}