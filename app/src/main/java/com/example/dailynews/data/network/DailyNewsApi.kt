package com.example.dailynews.data.network

import com.example.dailynews.data.network.model.NewsResponse
import com.example.dailynews.data.network.model.SourcesResponse
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

  @GET("top-headlines")
  suspend fun getNewsForCategory(
    @Query("country") country: String,
    @Query("apiKey") apiKey: String,
    @Query("pageSize") pageSize: Int = 100,
    @Query("page") page: Int = 1,
    @Query("category") category: String,
  ): Response<NewsResponse>

  @GET("everything")
  suspend fun searchNews(
    @Query("q") query: String,
    @Query("apiKey") apiKey: String,
  ): Response<NewsResponse>

  @GET("top-headlines/sources")
  suspend fun getAllSources(
    @Query("apiKey") apiKey: String,
  ): Response<SourcesResponse>
}