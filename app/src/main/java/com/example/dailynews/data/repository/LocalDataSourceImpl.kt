package com.example.dailynews.data.repository

import androidx.lifecycle.LiveData
import com.example.dailynews.data.db.dao.ArticleDao
import com.example.dailynews.data.db.dao.SourceDao
import com.example.dailynews.data.db.dao.SourceXDao
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.db.entities.SourceEntity
import com.example.dailynews.data.db.entities.SourceXEntity
import com.example.dailynews.data.network.enums.NewsCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
  private val articleDao: ArticleDao,
  private val sourceDao: SourceDao,
  private val sourceXDao: SourceXDao,
  private val ioDispatcher: CoroutineDispatcher,
) : LocalDataSource {
  override fun getAllArticles(): LiveData<List<ArticleEntity>> {
    return articleDao.getAllArticles()
  }

  override fun getAllArticlesFlow(): Flow<List<ArticleEntity>> {
    return articleDao.getAllArticlesFlow()
  }

  override fun getArticlesForCategory(category: NewsCategory): Flow<List<ArticleEntity>> {
    return articleDao.getArticlesForCategory(category.category)
  }

  override fun getAllSources(): Flow<List<SourceXEntity>> {
    return sourceXDao.getAllSources()
  }

  override fun getAllBookmarkedArticles(): Flow<List<ArticleEntity>> = articleDao.getBookmarkedArticles()

  override suspend fun getSourcesByArticleID(articleID: String): SourceEntity = sourceDao.getSourcesByArticleID(articleID)

  override suspend fun insertArticle(articleEntity: ArticleEntity) {
    withContext(ioDispatcher) {
      articleDao.insertArticle(articleEntity)
      articleEntity.sourceEntity?.let { safeSource -> sourceDao.insertSource(safeSource) }
    }
  }

  override suspend fun insertAllArticles(articleEntities: List<ArticleEntity>) {
    withContext(ioDispatcher) {
      val sources: ArrayList<SourceEntity> = arrayListOf()
      articleEntities.forEach { ae ->
        ae.sourceEntity?.let { safeAe -> sources.add(safeAe) }
      }
      if (articleEntities.isNotEmpty() && sources.isNotEmpty()) {
        deleteAllArticles()
      }
      articleDao.insertArticles(articleEntities)
      sourceDao.insertSources(sources)
    }
  }

  override suspend fun bookmarkArticle(articleEntity: ArticleEntity, addOrRemove: Int) {
    withContext(ioDispatcher) {
      articleDao.bookmarkArticle(articleEntity.articleID, addOrRemove)

//      articleEntity.isBookmarked = addOrRemove
//      articleDao.bookmarkArticle(articleEntity)
    }
  }

  override suspend fun insertAllSources(sources: List<SourceXEntity>) {
    sourceXDao.insertAllSources(sources)
  }

  override suspend fun deleteAllArticles() {
    withContext(Dispatchers.IO) {
      articleDao.deleteAllArticles()
      sourceDao.deleteAllSources()
    }
  }

}