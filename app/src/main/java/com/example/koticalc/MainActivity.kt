package com.example.koticalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.koticalc.ui.theme.KotiCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotiCalcTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CalculatorView()
                }
            }
        }
    }
}

@Composable
fun CalculatorView() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pantalla para mostrar entrada y resultado
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = input,
                fontSize = 36.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = Color.White,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = result,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = Color.Gray,
                maxLines = 1
            )
        }

        // Grid de botones
        val buttons = listOf(
            listOf("C", "(", ")", "%"),
            listOf("7", "8", "9", "÷"),
            listOf("4", "5", "6", "×"),
            listOf("1", "2", "3", "−"),
            listOf("+/-", "0", ".", "+"),
            listOf("=", "=")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { symbol ->
                    CalculatorButton(symbol, onClick = {
                        when (symbol) {
                            "=" -> result = calculateResult(input)
                            "C" -> {
                                input = ""
                                result = ""
                            }
                            else -> input += symbol
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(Color.DarkGray)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        // Cambiar BasicText a Text para evitar el error
        Text(
            text = symbol,
            fontSize = 32.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

// Función para evaluar el resultado
fun calculateResult(input: String): String {
    // Lógica de evaluación matemática para calcular
    return try {
        // Resultados de ejemplo para simplificar
        input // Reemplazar con un evaluador de expresiones real
    } catch (e: Exception) {
        "Error"
    }
}

// Vista previa de la calculadora
@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    KotiCalcTheme {
        CalculatorView()
    }
}
