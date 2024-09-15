package com.example.koticalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.koticalc.ui.theme.KotiCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotiCalcTheme {
                var currentView by remember { mutableStateOf("Calculator") }
                var input by remember { mutableStateOf("") }
                val history = remember { mutableStateListOf<Pair<String, String>>() } // Guardar operaciones y resultados

                Surface(color = Color(0xFF121212)) {
                    when (currentView) {
                        "Calculator" -> CalculatorView(
                            input = input,
                            history = history,
                            onNavigateToHistory = { currentView = "History" },
                            onInputChange = { input = it },
                            onAddToHistory = { operation, result ->
                                history.add(operation to result) // Guardar operación y resultado en el historial
                            }
                        )
                        "History" -> HistoryView(
                            history = history,
                            onOperationSelect = {
                                input = it
                                currentView = "Calculator"
                            },
                            onBack = { currentView = "Calculator" } // Volver a la calculadora
                        )
                    }
                }
            }
        }
    }
}
