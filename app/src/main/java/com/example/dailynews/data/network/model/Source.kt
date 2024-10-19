package com.example.dailynews.data.network.model


import com.example.dailynews.data.db.entities.SourceEntity
import com.google.gson.annotations.SerializedName


data class Source(
  @SerializedName("id")
  val id: String?,
  @SerializedName("name")
  val name: String?
)

fun Source.toEntity(articleID: Int) = SourceEntity(0, articleID, id, name)