package com.example.beering.data

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("agreements") val agreements: List<MemberAgreements>
    )

data class MemberAgreements(
    @SerializedName("name") val name: String,
    @SerializedName("isAgreed") val isAgreed:Boolean
)

data class MemberResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("responseCode") val responseCode: Int,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("result") val result: List<MemberResult>
    )

data class MemberResult(
    @SerializedName("fieldName") val fieldName: String,
    @SerializedName("rejectValue") val rejectValue: String,
    @SerializedName("message") val message: String
    )
