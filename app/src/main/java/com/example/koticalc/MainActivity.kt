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

                Surface(color = Color(0xFF121212)) {
                    when (currentView) {
                        "Calculator" -> CalculatorView(
                            input = input,
                            onNavigateToHistory = { currentView = "History" },
                            onInputChange = { input = it }
                        )
                        "History" -> HistoryView(
                            onOperationSelect = {
                                input = it
                                currentView = "Calculator"
                            }
                        )
                    }
                }
            }
        }
    }
}
