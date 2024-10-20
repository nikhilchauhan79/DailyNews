package com.example.dailynews.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyCircularIndicator(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .size(100.dp)
      .border(1.dp, color = Color.Blue.copy(alpha = 0.5f),
        shape = RoundedCornerShape(16.dp)
      )
      .background(shape = RoundedCornerShape(16.dp), color = Color.White),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}