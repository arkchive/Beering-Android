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
    @SerializedName("isAgreed") var isAgreed:Boolean
)

data class MemberResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("responseCode") val responseCode: Int,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("result") val result: MemberResult
    )

data class MemberResult(
    @SerializedName("message") val message: String,
    @SerializedName("objectName") val objectName: String,
    @SerializedName("errors") val errors: List<MemberErrors>

    )

data class MemberErrors(
    @SerializedName("fieldName") val fieldName: String,
    @SerializedName("rejectValue") val rejectValue: String,
    @SerializedName("message") val message: String
)
