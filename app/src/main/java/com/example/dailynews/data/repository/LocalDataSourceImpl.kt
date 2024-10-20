package com.example.dailynews.data.repository

import androidx.lifecycle.LiveData
import com.example.dailynews.data.db.dao.ArticleDao
import com.example.dailynews.data.db.dao.SourceDao
import com.example.dailynews.data.db.dao.SourceXDao
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.db.entities.SourceEntity
import com.example.dailynews.data.db.entities.SourceXEntity
import com.example.dailynews.data.network.model.SourceX
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

  override fun getAllSources(): Flow<List<SourceXEntity>> {
    return sourceXDao.getAllSources()
  }

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
//        deleteAllArticles()
      }
      articleDao.insertArticles(articleEntities)
      sourceDao.insertSources(sources)
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