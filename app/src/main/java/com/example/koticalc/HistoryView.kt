package com.example.koticalc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White

@Composable
fun HistoryView(
    history: List<Pair<String, String>>, // Lista de historial con pares de operación y resultado
    onOperationSelect: (String) -> Unit,
    onBack: () -> Unit // Función para regresar a la calculadora
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E1E), Color(0xFF121212))
                )
            )
            .padding(16.dp)
    ) {
        // Botón para volver estilizado
        Text(
            text = "← Volver",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onBack() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar historial con cartas
        history.forEach { (operation, result) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onOperationSelect(operation) }
                    .shadow(4.dp, RoundedCornerShape(8.dp)),
                colors = CardDefaults.cardColors(containerColor = Gray),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    // Operación
                    Text(
                        text = operation,
                        fontSize = 20.sp,
                        color = White,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    // Resultado
                    Text(
                        text = "= $result",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
