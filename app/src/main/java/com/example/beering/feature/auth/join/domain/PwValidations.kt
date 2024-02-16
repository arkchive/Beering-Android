package com.example.beering.feature.auth.join.domain

data class PwValidations(
    val englishChars : Boolean,     // 영문인지
    val specialChars : Boolean,     // 특수문자 넣었는지
    val numbers : Boolean,          // 숫자 넣었는지
    val length : Boolean,           // 글자제한
    val isConfirmed : Boolean,      // 재입력 일치
    val valid : Boolean             // 전체 형식 만족
)
