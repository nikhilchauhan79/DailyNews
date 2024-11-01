package com.example.dailynews.utils

import android.util.Log
import com.example.dailynews.data.network.enums.NewsCategory
import com.example.dailynews.data.network.model.NewsResponse
import com.example.dailynews.data.network.model.SourcesResponse
import com.example.dailynews.data.network.model.toEntity

object RemoteToLocalMapper {
  const val TAG = "RemoteToLocalMapper"

  inline fun <reified A, reified L> mapRemoteToLocal(remoteData: A, category: NewsCategory = NewsCategory.General): L {
    Log.d(TAG, "mapRemoteToLocal: ${L::class.java}")
    when {
      A::class.java == NewsResponse::class.java && L::class.java == List::class.java -> {
        val newsResponse = (remoteData as NewsResponse)
        return (newsResponse.articles?.map {
          it?.toEntity(category)
        } ?: listOf()) as L
      }

      A::class.java == SourcesResponse::class.java && L::class.java == List::class.java -> {
        val newsResponse = (remoteData as SourcesResponse)
        return (newsResponse.sources?.map {
          it.toEntity()
        } ?: listOf()) as L
      }

      else -> throw Exception("Unsupported type ${A::class.java} && ${L::class.java}")
    }
  }
}