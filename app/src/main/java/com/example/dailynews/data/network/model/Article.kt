package com.example.dailynews.data.network.model


import com.example.dailynews.data.db.entities.ArticleEntity
import com.google.gson.annotations.SerializedName

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
    0, author, content, description, publishedAt, title, url, urlToImage
  )

  ae.sourceEntity = source?.toEntity(0)
  return ae
}