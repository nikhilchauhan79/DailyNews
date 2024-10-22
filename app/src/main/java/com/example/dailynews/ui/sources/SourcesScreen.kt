package com.example.dailynews.ui.sources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dailynews.data.db.entities.SourceXEntity
import com.example.dailynews.data.network.model.SourceX

@Composable
fun SourcesScreen(
  modifier: Modifier = Modifier,
  sources: List<SourceXEntity>
) {
  LazyColumn(
    modifier = modifier.fillMaxSize(),
    contentPadding = PaddingValues(horizontal = 8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(sources) { sourceXEntity ->
      SourceItem(sourceXEntity = sourceXEntity)
    }
  }
}

@Composable
fun SourceItem(modifier: Modifier = Modifier, sourceXEntity: SourceXEntity) {
  Card(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(
      modifier = modifier.fillMaxWidth().padding(16.dp),
    ) {
      Text(sourceXEntity.name.toString())
      Spacer(modifier = Modifier.height(8.dp))
      Text(sourceXEntity.description.toString())

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(sourceXEntity.language.toString())
        Text(sourceXEntity.country.toString())
        Text(sourceXEntity.category.toString())
      }
    }
  }
}