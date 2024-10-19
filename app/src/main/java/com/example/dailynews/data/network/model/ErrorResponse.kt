package com.example.dailynews.data.network.model


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)