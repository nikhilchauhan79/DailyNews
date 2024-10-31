package com.example.dailynews.data.db.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class SourceEntity(
  @PrimaryKey
  val uid: String,
  val sourceID: String?,
  val articleID: String,
  @ColumnInfo("id")
  val id: String?,
  @ColumnInfo("name")
  val name: String?
)