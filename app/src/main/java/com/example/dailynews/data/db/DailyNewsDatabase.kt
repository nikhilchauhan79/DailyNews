package com.example.dailynews.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailynews.data.db.dao.ArticleDao
import com.example.dailynews.data.db.dao.SourceDao
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.db.entities.SourceEntity

@Database(entities = [ArticleEntity::class, SourceEntity::class], version = 1)
abstract class DailyNewsDatabase : RoomDatabase() {
  abstract fun articleDao(): ArticleDao
  abstract fun sourceDao(): SourceDao

  companion object {
    private lateinit var dailyNewsDatabase: DailyNewsDatabase
    private const val DATABASE_NAME = "dailyNews.db"

    fun getInstance(context: Context): DailyNewsDatabase {
      synchronized(this) {
        if (!::dailyNewsDatabase.isInitialized) {
          dailyNewsDatabase = Room.databaseBuilder(
            context.applicationContext, DailyNewsDatabase::class.java,
            DATABASE_NAME
          ).build()
        }
        return dailyNewsDatabase
      }
    }
  }
}