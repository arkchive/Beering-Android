package com.example.beering.feature.auth.join.view

import android.util.Log
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

    // 다음 버튼 활성화 조건
    private val _idCheck = MutableLiveData<DuplicationCheck>()   // 아이디 중복확인
    val idCheck: LiveData<DuplicationCheck> = _idCheck
    private val _nicknameCheck = MutableLiveData<DuplicationCheck>()     // 닉네임 중복확인
    val nicknameCheck: LiveData<DuplicationCheck> = _nicknameCheck
    private val _validNext = MutableLiveData<Boolean>()     // 최종 활성화 여부
    val validNext: LiveData<Boolean> = _validNext

    init{
        _userId.value = ""
        _password.value = ""
        _passwordAgain.value = ""
        _name.value = ""
        _idCheck.value = DuplicationCheck.PROCEEDING
        _nicknameCheck.value = DuplicationCheck.PROCEEDING
    }
    fun setUserId(id : String){
        _userId.value = id
        _idCheck.value = DuplicationCheck.PROCEEDING
    }

    fun setPassword(pw : String){
        _password.value = pw
        _pwValidation.value = signUp.validatePw(pw, passwordAgain.value!!)
        validNext()
    }

    fun setPasswordAgain(pwAgain : String){
        _passwordAgain.value = pwAgain
        _pwValidation.value = signUp.validatePw(password.value!!, pwAgain)
        validNext()
    }

    fun setName(name : String){
        _name.value = name
        _nicknameValidation.value = signUp.validateName(name)
        _nicknameCheck.value = DuplicationCheck.PROCEEDING
    }

    fun validNext(){
        Log.d("pwpw", pwValidation.value!!.toString())
        if (pwValidation.value == null){
            return
        }
        _validNext.value = (pwValidation.value!!.valid
                && pwValidation.value!!.isConfirmed
                && nicknameCheck.value == DuplicationCheck.CHECKED
                && idCheck.value == DuplicationCheck.CHECKED)
    }

    suspend fun checkId(id : String){
        signUp.checkId(id)
        // TODO : 중복이면 idcheck = false 아니면 true
        validNext()
    }

    suspend fun checkNickname(name : String){
        signUp.checkNickname(name)
        // TODO : 중복이면 nicknameCheck = false 아니면 true
        validNext()
    }

    // 뷰모델 의존성 주입을 위한 Factory
    companion object {
        enum class DuplicationCheck{
            PROCEEDING, UNCHECKED, CHECKED
        }

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