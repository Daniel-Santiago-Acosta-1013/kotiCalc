package com.example.koticalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotiCalcTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF121212)) { // Fondo oscuro
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
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = Color.White,
                maxLines = 1
            )
        }

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
                            "=" -> result = calculateResult(input) // Calcular el resultado
                            "C" -> {
                                input = ""
                                result = ""
                            }
                            "+/-" -> { // Cambiar el signo
                                if (input.isNotEmpty()) {
                                    input = if (input.startsWith("-")) input.drop(1) else "-$input"
                                }
                            }
                            "÷" -> input += "/"
                            "×" -> input += "*"
                            "()" -> input += addParenthesis(input)
                            else -> input += symbol
                        }
                    })
                }
            }
        }
    }
}

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

// Función de ayuda para verificar si un carácter es un operador
fun Char.isOperator(): Boolean = this == '+' || this == '-' || this == '*' || this == '/'

// Función para evaluar el resultado
fun calculateResult(input: String): String {
    return try {
        // Uso de la librería exp4j para evaluar expresiones matemáticas
        val expression = ExpressionBuilder(input).build()
        val result = expression.evaluate()
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}

@Composable
fun CalculatorButton(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(50)) // Botón oscuro
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            fontSize = 32.sp,
            color = Color.White, // Texto en blanco para los botones
            textAlign = TextAlign.Center
        )
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
