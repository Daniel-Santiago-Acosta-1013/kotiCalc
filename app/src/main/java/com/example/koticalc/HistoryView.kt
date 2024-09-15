package com.example.koticalc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryView(
    history: List<Pair<String, String>>, // Lista de historial con pares de operación y resultado
    onOperationSelect: (String) -> Unit,
    onBack: () -> Unit // Función para regresar a la calculadora
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón para volver
        Text(
            text = "← Volver",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onBack() } // Volver a la calculadora
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar historial
        history.forEach { (operation, result) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onOperationSelect(operation) } // Selecciona la operación
            ) {
                Text(
                    text = "$operation = $result",
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
        }
    }
}
