package com.example.dailynews.data.db.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class SourceXEntity(
  @PrimaryKey
  val uid: String,
  @ColumnInfo(name = "category")
  val category: String?,
  @ColumnInfo(name = "country")
  val country: String?,
  @ColumnInfo(name = "description")
  val description: String?,
  @ColumnInfo(name = "id")
  val id: String?,
  @ColumnInfo(name = "language")
  val language: String?,
  @ColumnInfo(name = "name")
  val name: String?,
  @ColumnInfo(name = "url")
  val url: String?
)