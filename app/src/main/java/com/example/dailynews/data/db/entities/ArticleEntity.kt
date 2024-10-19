package com.example.dailynews.data.db.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
  @PrimaryKey(autoGenerate = true)
  val articleID: Int,
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
  val urlToImage: String?
) {
  @Ignore
  var sourceEntity: SourceEntity? = null
}