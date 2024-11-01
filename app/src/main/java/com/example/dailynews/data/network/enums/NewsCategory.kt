package com.example.dailynews.data.network.enums

import androidx.annotation.DrawableRes
import com.example.dailynews.R

sealed class NewsCategory(val category: String, @DrawableRes val icon: Int, val index: Int) {
  data object General : NewsCategory("general", R.drawable.ic_person_pin, 0)
  data object Business : NewsCategory("business", R.drawable.ic_business, 1)
  data object Health : NewsCategory("health", R.drawable.ic_health, 2)
  data object Science : NewsCategory("science", R.drawable.ic_science, 3)
  data object Sports : NewsCategory("sports", R.drawable.ic_sports, 4)
  data object Technology : NewsCategory("technology", R.drawable.ic_mobile, 5)
  data object Entertainment : NewsCategory("entertainment", R.drawable.ic_music, 6)
}