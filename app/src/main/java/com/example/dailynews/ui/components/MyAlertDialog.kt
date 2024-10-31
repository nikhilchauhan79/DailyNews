package com.example.dailynews.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dailynews.data.db.entities.ArticleEntity

@Composable
fun MyAlertDialog(
  modifier: Modifier = Modifier,
  openDialog: Boolean,
  dialogCallback: (Boolean, ArticleEntity) -> Unit,
  article: ArticleEntity,
  showDialogListener: (Boolean) -> Unit
) {
  AlertDialog(
    onDismissRequest = {
      showDialogListener.invoke(false)
    },
    confirmButton = {
      TextButton (onClick = {
        dialogCallback.invoke(true, article)
      }) {
        Text("Confirm")
      }
    },
    dismissButton = {
      TextButton (onClick = {
        showDialogListener.invoke(false)
      }) {
        Text("Cancel")
      }
    },
    modifier = modifier.fillMaxWidth(),
    title = {
      Text("Are you sure?")
    },
    text = {
      Text("The selected bookmark will be deleted")
    }
  )
}