package com.example.dailynews.data.network.model


import com.example.dailynews.data.db.entities.SourceEntity
import com.example.dailynews.data.db.entities.SourceXEntity
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class SourceX(
  @SerializedName("category")
  val category: String?,
  @SerializedName("country")
  val country: String?,
  @SerializedName("description")
  val description: String?,
  @SerializedName("id")
  val id: String?,
  @SerializedName("language")
  val language: String?,
  @SerializedName("name")
  val name: String?,
  @SerializedName("url")
  val url: String?
)

fun SourceX.toEntity(): SourceXEntity {
  return SourceXEntity(UUID.randomUUID().toString(), category, country, description, id, language, name, url)
}