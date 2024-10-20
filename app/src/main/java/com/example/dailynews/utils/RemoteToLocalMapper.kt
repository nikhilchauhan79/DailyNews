package com.example.dailynews.utils

import android.util.Log
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.network.model.NewsResponse
import com.example.dailynews.data.network.model.toEntity
import com.google.gson.reflect.TypeToken

object RemoteToLocalMapper {
  const val TAG = "RemoteToLocalMapper"
  inline fun <reified A, reified L> mapRemoteToLocal(remoteData: A): L {
    val typeToken = object : TypeToken<List<ArticleEntity>>() {}.type
    Log.d(TAG, "mapRemoteToLocal: ${L::class.java}")
    when {
      A::class.java == NewsResponse::class.java && L::class == List::class -> {
        val newsResponse = (remoteData as NewsResponse)
        return (newsResponse.articles?.map {
          it?.toEntity()
        } ?: listOf()) as L
      }

      else -> throw Exception("Unsupported type ${A::class.java} && ${L::class.java}")
    }
  }
}