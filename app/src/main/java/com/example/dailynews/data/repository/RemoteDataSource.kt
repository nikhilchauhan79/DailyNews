package com.example.dailynews.data.repository

import com.example.dailynews.data.network.DailyNewsApi
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.data.network.model.ErrorResponse
import com.example.dailynews.data.network.model.NewsResponse
import com.example.dailynews.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import java.io.Reader

class RemoteDataSource(
  private val dailyNewsApi: DailyNewsApi,
  private val ioDispatcher: CoroutineDispatcher
) {

  fun getTopHeadlines(): Flow<NetworkResult<NewsResponse?>> =
    flow<NetworkResult<NewsResponse?>> {
      try {
        val response = dailyNewsApi.getTopHeadlines("us", Constants.API_KEY)
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

    }.catch {
      emit(
        NetworkResult.Failed(
          "Unable to fetch data with exception: ${it.message}"
        )
      )
    }.flowOn(ioDispatcher)

  companion object {
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
