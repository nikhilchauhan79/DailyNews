package com.example.dailynews.data.db.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class ArticleEntity(
  @PrimaryKey
  val articleID: String,
  @ColumnInfo("author")
  val author: String?,
  @ColumnInfo("content")
  val content: String?,
  @ColumnInfo("description")
  val description: String?,
  @ColumnInfo("publishedAt")
  val publishedAt: String?,
  @ColumnInfo("title")
  val title: String?,
  @ColumnInfo("url")
  val url: String?,
  @ColumnInfo("urlToImage")
  val urlToImage: String?,
  @ColumnInfo("isBookmarked")
  var isBookmarked: Int = 0
) {
  @Ignore
  var sourceEntity: SourceEntity? = null
}