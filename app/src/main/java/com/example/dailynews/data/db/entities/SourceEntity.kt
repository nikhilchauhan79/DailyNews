package com.example.dailynews.data.db.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SourceEntity(
  @PrimaryKey(autoGenerate = true)
  val sourceID: Int,
  val articleID: Int,
  @ColumnInfo("id")
  val id: String?,
  @ColumnInfo("name")
  val name: String?
)