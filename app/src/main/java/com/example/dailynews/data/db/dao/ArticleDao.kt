package com.example.dailynews.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.dailynews.data.db.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
  @Query("select * from articleentity")
  fun getAllArticles(): LiveData<List<ArticleEntity>>

  @Query("select * from articleentity")
  fun getAllArticlesFlow(): Flow<List<ArticleEntity>>

  @Query("select * from articleentity where category = :category")
  fun getArticlesForCategory(category: String): Flow<List<ArticleEntity>>

  @Query("select * from articleentity where isBookmarked = 1")
  fun getBookmarkedArticles(): Flow<List<ArticleEntity>>

  @Query("select * from articleentity where publishedAt =:publishedAt and url = :url and urlToImage = :urlToImage")
  fun getArticleByContent(publishedAt: String, url: String, urlToImage: String) : ArticleEntity?

  @Query("select * from articleentity where articleID = :id")
  fun getArticleByID(id: String): ArticleEntity

  @Query("update articleentity set isBookmarked = :addOrRemove where articleID = :uid")
  fun bookmarkArticle(uid: String, addOrRemove: Int)

  @Update
  suspend fun bookmarkArticle(articleEntity: ArticleEntity)

  @Insert
  fun insertArticle(articleEntity: ArticleEntity)

  @Insert
  fun insertArticles(articleEntities: List<ArticleEntity>)

  @Transaction
  fun upsertArticle(articles: List<ArticleEntity>) {
    val freshArticles = articles.filter {
      val duplicateArticle =
        if (it.publishedAt != null && it.url != null && it.urlToImage != null) {
          getArticleByContent(it.publishedAt, it.url, it.urlToImage)
        } else null

      if(duplicateArticle != null) it.category != duplicateArticle.category else true
    }
    insertArticles(freshArticles)
  }

  @Query("delete from articleentity")
  fun deleteAllArticles()
}