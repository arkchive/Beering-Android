package com.example.beering.feature.auth.join.domain

data class PwValidations(
    val englishChars : Boolean,
    val specialChars : Boolean,
    val numbers : Boolean,
    val length : Boolean,
    val valid : Boolean
)
