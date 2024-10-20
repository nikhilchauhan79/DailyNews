package com.example.dailynews.data.network.model


import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("sources")
    val sources: List<SourceX>?,
    @SerializedName("status")
    val status: String?
)