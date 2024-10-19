package com.example.dailynews.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dailynews.data.repository.NewsRepository
import com.example.dailynews.viewmodels.DailyNewsViewModel

class MyViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(DailyNewsViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return DailyNewsViewModel(newsRepository) as T
    }

    throw IllegalArgumentException("Unknown ViewModel class")
  }
}