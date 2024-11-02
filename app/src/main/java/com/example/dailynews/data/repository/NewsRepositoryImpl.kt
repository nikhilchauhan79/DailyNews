package com.example.dailynews.data.repository

import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.db.entities.SourceXEntity
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.data.network.enums.NewsCategory
import com.example.dailynews.utils.RemoteToLocalMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

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

  override fun getNewsForCategory(category: NewsCategory): Flow<NetworkResult<List<ArticleEntity>>> =
    performNetworkCallAndSaveData(
      {
        localDataSource.getArticlesForCategory(category)
      },
      {
        remoteDataSource.getNewsForCategory(category)
      },
      {
        RemoteToLocalMapper.mapRemoteToLocal(it, category)
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

  override fun getSources(): Flow<NetworkResult<List<SourceXEntity>>> =
    performNetworkCallAndSaveData(
      {
        localDataSource.getAllSources()
      },
      {
        remoteDataSource.getNewsSources()
      },
      {
        RemoteToLocalMapper.mapRemoteToLocal(it)
      },
      {
        localDataSource.insertAllSources(it)
      },
      listOf(),
      true
    )

  override fun getBookmarkedArticles(): Flow<List<ArticleEntity>> =
    localDataSource.getAllBookmarkedArticles().map { article ->
      article.map { ae ->
        withContext(Dispatchers.IO) {
          ae.sourceEntity = localDataSource.getSourcesByArticleID(ae.articleID)
          ae
        }
      }
    }.flowOn(Dispatchers.IO)


  override suspend fun bookmarkArticle(articleEntity: ArticleEntity, addOrRemove: Int) {
    localDataSource.bookmarkArticle(articleEntity, addOrRemove)
  }

  override fun getAllArticlesFromDB(): Flow<List<ArticleEntity>> {
    return localDataSource.getAllArticlesFlow()
  }


  private fun <A, L> performNetworkCallAndSaveData(
    fetchFromLocal: suspend () -> Flow<L>,
    fetchFromRemote: suspend () -> Flow<NetworkResult<A>>,
    mapToLocal: (A) -> L,
    saveToDatabase: suspend (L) -> Unit,
    defaultValue: L,
    shouldFetchFromLocal: Boolean
  ): Flow<NetworkResult<L>> = flow {
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

  @OptIn(ExperimentalCoroutinesApi::class)
  private fun <A, L> performNetworkCallAndObserveFromDB(
    fetchFromLocal: suspend () -> Flow<L>,
    fetchFromRemote: suspend () -> Flow<NetworkResult<A>>,
    mapToLocal: (A) -> L,
    saveToDatabase: suspend (L) -> Unit,
    defaultValue: L,
    shouldFetchFromLocal: Boolean,
    observeFromDB: Flow<L>
  ): Flow<L> = flow {
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
  }.flatMapConcat {
    observeFromDB
  }.flowOn(ioDispatcher)
}

