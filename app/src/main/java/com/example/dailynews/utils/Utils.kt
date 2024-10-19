package com.example.dailynews.utils

import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object Utils {

  fun formatDate(date: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")

    val formattedDate = try {
      val utcDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)

      // Convert to local time zone (system default)
      val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault())

      // Define the output format

      // Format the date-time to local format
      localDateTime.format(formatter)

    } catch (e: Exception) {
      val utcDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault())

      // Convert to local time zone (system default)
      val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault())

      // Define the output format

      // Format the date-time to local format
      localDateTime.format(formatter)
    }
    return formattedDate
  }

}