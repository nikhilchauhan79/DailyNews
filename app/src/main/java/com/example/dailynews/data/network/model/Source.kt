package com.example.dailynews.data.network.model


import com.example.dailynews.data.db.entities.SourceEntity
import com.google.gson.annotations.SerializedName
import java.util.UUID


data class Source(
  @SerializedName("id")
  val id: String?,
  @SerializedName("name")
  val name: String?
)

fun Source.toEntity(articleID: String) = SourceEntity(UUID.randomUUID().toString(),id, articleID, id, name)