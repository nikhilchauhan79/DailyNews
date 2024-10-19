package com.example.dailynews.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DailyNewsService {
  private const val DAILY_NEWS_URL = "https://newsapi.org/v2/"

  private fun getInstance(): Retrofit =
    Retrofit.Builder().baseUrl(DAILY_NEWS_URL).addConverterFactory(GsonConverterFactory.create())
      .client(getOkHttpClient())
      .build()

  private fun getHttpInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

  private fun getOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().addInterceptor(
    getHttpInterceptor()
  ).build()

  fun getApiService(): DailyNewsApi = getInstance().create(DailyNewsApi::class.java)
}