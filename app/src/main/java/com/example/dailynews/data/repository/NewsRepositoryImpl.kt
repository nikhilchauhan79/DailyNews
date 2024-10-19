package com.example.dailynews.data.repository

import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.data.network.model.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepositoryImpl(
  private val localDataSource: LocalDataSource,
  private val remoteDataSource: RemoteDataSource,
  private val ioDispatcher: CoroutineDispatcher
) : NewsRepository {

  override fun getTopHeadlines(): Flow<NetworkResult<List<ArticleEntity>>> =
    flow<NetworkResult<List<ArticleEntity>>> {
      emit(NetworkResult.Loading())
      val localArticles = localDataSource.getAllArticlesFlow().firstOrNull()

      when (val remoteResult = remoteDataSource.getTopHeadlines().first()) {
        is NetworkResult.Failed -> {
          emit(NetworkResult.Success(localArticles ?: listOf()))
        }

        is NetworkResult.Loading -> {
//          emit(NetworkResult.Loading())
        }

        is NetworkResult.Success -> {
          remoteResult.response?.articles?.let { safeArticles ->
            val articles = safeArticles.mapNotNull {
              it?.toEntity()
            }
            emit(NetworkResult.Success(articles))
            localDataSource.insertAllArticles(articles)
          }
        }
      }
    }.flowOn(ioDispatcher)

}