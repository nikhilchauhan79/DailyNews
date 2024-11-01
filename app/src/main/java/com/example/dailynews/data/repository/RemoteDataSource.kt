package com.example.dailynews.data.repository

import android.util.Log
import com.example.dailynews.data.network.DailyNewsApi
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.data.network.enums.NewsCategory
import com.example.dailynews.data.network.model.ErrorResponse
import com.example.dailynews.data.network.model.NewsResponse
import com.example.dailynews.data.network.model.SourcesResponse
import com.example.dailynews.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException
import java.io.Reader

class RemoteDataSource(
  private val dailyNewsApi: DailyNewsApi,
  private val ioDispatcher: CoroutineDispatcher
) {

  private fun <T> performNetworkRequest(networkRequest: suspend () -> Response<T>): Flow<NetworkResult<T>> =
    flow<NetworkResult<T>> {
      try {
        val response = networkRequest.invoke()
        if (!response.isSuccessful) {
          response.errorBody()?.charStream()?.let { safeError ->
            val error = parseError(safeError)
            emit(
              NetworkResult.Failed(
                error?.message
                  ?: "Unknown error: with code: ${response.code()} , message :${response.message()}"
              )
            )
          } ?: run {
            emit(
              NetworkResult.Failed(
                "Unknown error: with code: ${response.code()} , message :${response.message()}"
              )
            )
          }
        } else {
          val newsResponse = response.body()
          if (newsResponse != null) {
            emit(NetworkResult.Success(newsResponse))
          } else {
            emit(
              NetworkResult.Failed(
                "Response body is null"
              )
            )
          }
        }
      } catch (e: Exception) {
        throw e
      }

    }.catch { e ->
      Log.d(TAG, "performNetworkRequest: " + e.message)
      emit(
        NetworkResult.Failed(
          "Unable to fetch data with exception: ${e.message}"
        )
      )

    }.flowOn(ioDispatcher)

  fun getTopHeadlines(): Flow<NetworkResult<NewsResponse>> = performNetworkRequest {
    dailyNewsApi.getTopHeadlines("us", Constants.API_KEY)
  }

  fun getNewsForCategory(category: NewsCategory): Flow<NetworkResult<NewsResponse>> =
    performNetworkRequest {
      dailyNewsApi.getNewsForCategory("us", Constants.API_KEY, category = category.category)
    }

  fun searchNews(searchQuery: String): Flow<NetworkResult<NewsResponse>> = performNetworkRequest {
    dailyNewsApi.searchNews(searchQuery, Constants.API_KEY)
  }

  fun getNewsSources(): Flow<NetworkResult<SourcesResponse>> =
    performNetworkRequest {
      dailyNewsApi.getAllSources(Constants.API_KEY)
    }


  companion object {
    const val TAG = "RemoteDataSource"
    fun parseError(response: Reader): ErrorResponse? {
      val gson = Gson()
      return try {
        gson.fromJson(response, ErrorResponse::class.java)
      } catch (e: IOException) {
        e.printStackTrace()
        null
      }
    }
  }
}
