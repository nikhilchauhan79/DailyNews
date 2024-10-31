package com.example.dailynews.data.network.model


import com.example.dailynews.data.db.entities.ArticleEntity
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Article(
  @SerializedName("author")
  val author: String?,
  @SerializedName("content")
  val content: String?,
  @SerializedName("description")
  val description: String?,
  @SerializedName("publishedAt")
  val publishedAt: String?,
  @SerializedName("source")
  val source: Source?,
  @SerializedName("title")
  val title: String?,
  @SerializedName("url")
  val url: String?,
  @SerializedName("urlToImage")
  val urlToImage: String?
)

fun Article.toEntity(): ArticleEntity {
  val ae = ArticleEntity(
    UUID.randomUUID().toString(), author, content, description, publishedAt, title, url, urlToImage
  )

  ae.sourceEntity = source?.toEntity(ae.articleID)
  return ae
}