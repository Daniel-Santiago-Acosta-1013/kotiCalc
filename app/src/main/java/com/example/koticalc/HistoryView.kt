package com.example.koticalc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryView(onOperationSelect: (String) -> Unit) {
    val history = remember { mutableStateListOf<String>("5+5=10", "12/3=4", "7*8=56") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        history.forEach { operation ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onOperationSelect(operation.split("=")[0]) } // Selecciona la operación
            ) {
                Text(
                    text = operation,
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
        }
    }
}
