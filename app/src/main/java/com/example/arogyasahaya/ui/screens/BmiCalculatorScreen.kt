package com.example.arogyasahaya.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun BmiCalculatorScreen() {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf<String?>(null) }
    var bmiCategory by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BMI Calculator",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val w = weight.toFloatOrNull()
                val h = height.toFloatOrNull()
                if (w != null && h != null && h > 0) {
                    val hMeters = h / 100
                    val bmi = w / (hMeters * hMeters)
                    bmiResult = String.format("%.1f", bmi)
                    bmiCategory = when {
                        bmi < 18.5 -> "Underweight"
                        bmi < 25 -> "Normal weight"
                        bmi < 30 -> "Overweight"
                        else -> "Obesity"
                    }
                } else {
                    bmiResult = "Invalid input"
                    bmiCategory = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }

        bmiResult?.let {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 16.dp)) {
                Text(text = "Your BMI: $it", style = MaterialTheme.typography.titleLarge)
                if (bmiCategory.isNotEmpty()) {
                    Text(text = "Category: $bmiCategory", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
