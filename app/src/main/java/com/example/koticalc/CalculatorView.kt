package com.example.koticalc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.ui.graphics.vector.ImageVector

fun Char.isOperator(): Boolean = this == '+' || this == '-' || this == '*' || this == '/'

// Función para agregar paréntesis inteligentemente
fun addParenthesis(input: String): String {
    val openCount = input.count { it == '(' }
    val closeCount = input.count { it == ')' }

    // Si no hay input o el último carácter es un operador o paréntesis de apertura, agregar paréntesis de apertura
    return if (openCount == closeCount || input.lastOrNull()?.isOperator() == true || input.isEmpty()) {
        "("
    } else {
        ")"
    }
}

// Función para evaluar el resultado
fun calculateResult(input: String): String {
    return try {
        // Uso de la librería exp4j para evaluar expresiones matemáticas
        val expression = ExpressionBuilder(input).build()
        val result = expression.evaluate()

        // Verificar si el resultado es entero o decimal
        if (result == result.toLong().toDouble()) {
            // Si el resultado es un número entero, devolverlo como entero
            result.toLong().toString()
        } else {
            // Si el resultado es decimal, devolverlo con decimales
            result.toString()
        }
    } catch (e: Exception) {
        "Error"
    }
}

// Separar la lógica para calcular el resultado
fun calculate(input: String): String {
    return calculateResult(input)
}


// Separar la lógica para eliminar el último carácter
fun deleteLastCharacter(input: String): String {
    return if (input.isNotEmpty()) input.dropLast(1) else input
}

@Composable
fun CalculatorButton(
    symbol: String? = null,
    icon: ImageVector? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(80.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(50)) // Botón oscuro
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = "History Icon", tint = Color.White)
        } else if (symbol != null) {
            Text(
                text = symbol,
                fontSize = 32.sp,
                color = Color.White, // Texto en blanco para los botones
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CalculatorView(
    input: String,
    onNavigateToHistory: () -> Unit,
    onInputChange: (String) -> Unit,
    onAddToHistory: (String, String) -> Unit // Función para agregar al historial
) {
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
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = Color.White,
                maxLines = 1
            )
        }

        // Botón de reloj y borrar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón de historial (Reloj)
            CalculatorButton(
                icon = Icons.Default.History, // Uso del ícono de historial
                onClick = onNavigateToHistory,
                modifier = Modifier.padding(bottom = 8.dp, start = 20.dp)
            )

            // Botón de borrar
            CalculatorButton(
                symbol = "⌫",
                onClick = { onInputChange(deleteLastCharacter(input)) },
                modifier = Modifier.padding(bottom = 8.dp, end = 20.dp)
            )
        }

        // Línea divisoria delgada
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))

        // Grid de botones
        val buttons = listOf(
            listOf("C", "()", "%", "÷"),
            listOf("7", "8", "9", "×"),
            listOf("4", "5", "6", "−"),
            listOf("1", "2", "3", "+"),
            listOf("+/-", "0", ".", "=")
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
                            "=" -> {
                                result = calculate(input)
                                onAddToHistory(input, result) // Guardar en historial
                            }
                            "C" -> {
                                onInputChange("")
                                result = ""
                            }
                            "+/-" -> {
                                if (input.isNotEmpty()) {
                                    onInputChange(if (input.startsWith("-")) input.drop(1) else "-$input")
                                }
                            }
                            "÷" -> onInputChange(input + "/")
                            "×" -> onInputChange(input + "*")
                            "()" -> onInputChange(input + addParenthesis(input))
                            else -> onInputChange(input + symbol)
                        }
                    })
                }
            }
        }
    }
}
