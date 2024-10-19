package com.example.dailynews.data.network

sealed class NetworkResult<T>(
  val response: T? = null,
  val message: String? = null
) {
  class Loading<T> : NetworkResult<T>()
  class Success<T>(response: T) : NetworkResult<T>(response)
  class Failed<T>(message: String?) : NetworkResult<T>(message = message)
}