package com.example.beering.feature.auth.join.domain

class SignupUseCase {

    suspend operator fun invoke(){
        // TODO : 실제 회원가입 요청 API 호출
    }

    suspend fun checkId(id : String){
        // TODO : 아이디 중복체크 API 호출
    }

    suspend fun checkNickname(name : String){
        // TODO : 닉네임 중복체크 API 호출
    }

    fun validatePw(pw : String, pwAgain : String) : PwValidations{
        // 비밀번호가 영문자를 포함하는지 확인
        val containsEnglishChars = pw.matches(Regex(".*[a-zA-Z].*"))
        // 비밀번호가 특수문자를 포함하는지 확인
        val containsSpecialChars = pw.matches(Regex(".*[!@#\\\$%].*"))
        // 비밀번호가 숫자를 포함하는지 확인
        val containsNumbers = pw.matches(Regex(".*[0-9].*"))
        // 비밀번호의 길이가 8자에서 20자 사이인지 확인
        val isLengthValid = pw.length in 8..20
        // 비밀번호 재입력과 일치하는지 확인
        val isConfirmed = pw.equals(pwAgain)

        val isValid = containsEnglishChars && containsSpecialChars && containsNumbers && isLengthValid

        return PwValidations(containsEnglishChars,containsSpecialChars, containsNumbers, isLengthValid, isConfirmed, isValid)
    }

    fun validateName(nickname : String) : NameValidations{
        val containsValidCharacters = nickname.matches(Regex("[a-zA-Zㄱ-ㅎ가-힣0-9]+"))
        val isLengthValid = nickname.length in 1..10

        val isValid = containsValidCharacters && isLengthValid
        return NameValidations(containsValidCharacters, isLengthValid, isValid)
    }
}