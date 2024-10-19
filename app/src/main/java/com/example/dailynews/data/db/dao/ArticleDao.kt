package com.example.dailynews.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailynews.data.db.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
  @Query("select * from articleentity")
  fun getAllArticles(): LiveData<List<ArticleEntity>>

  @Query("select * from articleentity")
  fun getAllArticlesFlow(): Flow<List<ArticleEntity>>

  @Query("select * from articleentity where articleID = :id")
  fun getArticleByID(id: String): ArticleEntity

  @Insert
  fun insertArticle(articleEntity: ArticleEntity)

  @Insert
  fun insertArticles(articleEntities: List<ArticleEntity>)

  @Query("delete from articleentity")
  fun deleteAllArticles()
}