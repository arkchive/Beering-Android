package com.example.beering.feature.auth.join.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.beering.feature.auth.join.domain.NameValidations
import com.example.beering.feature.auth.join.domain.PwValidations
import com.example.beering.feature.auth.join.domain.SignupUseCase

class JoinViewModel(
    private val signUp : SignupUseCase
) : ViewModel() {
    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private val _passwordAgain = MutableLiveData<String>()
    val passwordAgain: LiveData<String> = _passwordAgain
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _nicknameValidation = MutableLiveData<NameValidations>()
    val nicknameValidation: LiveData<NameValidations> = _nicknameValidation
    private val _pwValidation = MutableLiveData<PwValidations>()
    val pwValidation: LiveData<PwValidations> = _pwValidation

    init{
        _userId.value = ""
        _password.value = ""
        _passwordAgain.value = ""
        _name.value = ""
    }
    fun setUserId(id : String){
        _userId.value = id
    }

    fun setPassword(pw : String){
        _password.value = pw
        _pwValidation.value = signUp.validatePw(pw)
    }

    fun setPasswordAgain(pw : String){
        _passwordAgain.value = pw
    }

    fun setName(name : String){
        _name.value = name
        _nicknameValidation.value = signUp.validateName(name)
    }

    // 뷰모델 의존성 주입을 위한 Factory
    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
            ): T {
                val signupUseCase = SignupUseCase()

                return JoinViewModel(
                    signupUseCase
                ) as T
            }
        }
    }
}