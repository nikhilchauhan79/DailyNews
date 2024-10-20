package com.example.dailynews.data.network

import com.example.dailynews.data.network.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyNewsApi {
  @GET("top-headlines")
  suspend fun getTopHeadlines(
    @Query("country") country: String,
    @Query("apiKey") apiKey: String,
    @Query("pageSize") pageSize: Int = 100,
    @Query("page") page: Int = 1
  ): Response<NewsResponse>

  @GET("everything")
  suspend fun searchNews(
    @Query("q") query: String,
    @Query("apiKey") apiKey: String,
  ) : Response<NewsResponse>
}