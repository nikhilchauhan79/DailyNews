package com.example.dailynews.data.repository

import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.utils.RemoteToLocalMapper
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
    performNetworkCallAndSaveData(
      {
        localDataSource.getAllArticlesFlow()
      },
      {
        remoteDataSource.getTopHeadlines()
      },
      {
        RemoteToLocalMapper.mapRemoteToLocal(it)
      },
      {
        localDataSource.insertAllArticles(it)
      },
      listOf(),
      true
    )

  override fun searchNews(query: String): Flow<NetworkResult<List<ArticleEntity>>> =
    performNetworkCallAndSaveData(
      {
        localDataSource.getAllArticlesFlow()
      },
      {
        remoteDataSource.searchNews(query)
      },
      {
        RemoteToLocalMapper.mapRemoteToLocal(it)
      },
      {
        localDataSource.insertAllArticles(it)
      },
      listOf(),
      false
    )

//    flow<NetworkResult<List<ArticleEntity>>> {
//      emit(NetworkResult.Loading())
//      val localArticles = localDataSource.getAllArticlesFlow().firstOrNull()
//
//      when (val remoteResult = remoteDataSource.getTopHeadlines().first()) {
//        is NetworkResult.Failed -> {
//          emit(NetworkResult.Success(localArticles ?: listOf()))
//        }
//
//        is NetworkResult.Loading -> {
////          emit(NetworkResult.Loading())
//        }
//
//        is NetworkResult.Success -> {
//          remoteResult.response?.articles?.let { safeArticles ->
//            val articles = safeArticles.mapNotNull {
//              it?.toEntity()
//            }
//            emit(NetworkResult.Success(articles))
//            localDataSource.insertAllArticles(articles)
//          }
//        }
//      }
//    }.flowOn(ioDispatcher)

  private fun <A, L> performNetworkCallAndSaveData(
    fetchFromLocal: suspend () -> Flow<L>,
    fetchFromRemote: suspend () -> Flow<NetworkResult<A>>,
    mapToLocal: (A) -> L,
    saveToDatabase: suspend (L) -> Unit,
    defaultValue: L,
    shouldFetchFromLocal: Boolean
  ): Flow<NetworkResult<L>> = flow<NetworkResult<L>> {
    emit(NetworkResult.Loading())

    val localArticles = fetchFromLocal.invoke().firstOrNull()

    when (val remoteResult = fetchFromRemote.invoke().first()) {
      is NetworkResult.Failed -> {
        if (shouldFetchFromLocal) {
          emit(NetworkResult.Success<L>(localArticles ?: defaultValue))
        } else {
          emit(NetworkResult.Failed<L>(remoteResult.message))
        }
      }

      is NetworkResult.Loading -> {
//          emit(NetworkResult.Loading())
      }

      is NetworkResult.Success -> {
        remoteResult.response?.let {
          val mapped = mapToLocal(it)
          saveToDatabase(mapped)
          emit(NetworkResult.Success(mapped))
        }
      }
    }
  }.flowOn(ioDispatcher)
}