package com.example.beering.feature.auth.join.domain

data class NameValidations(
    val characters : Boolean,
    val length : Boolean,
    val valid : Boolean
)
