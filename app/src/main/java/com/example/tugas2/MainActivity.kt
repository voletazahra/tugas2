package com.example.tugas2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tugas2.ui.theme.Tugas2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tugas2Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "input_screen") {
                    composable("input_screen") {
                        InputScreen(navController)
                    }
                    composable("result_screen/{result}/{nim}/{nama}") { backStackEntry ->
                        val result = backStackEntry.arguments?.getString("result")
                        val nim = backStackEntry.arguments?.getString("nim")
                        val nama = backStackEntry.arguments?.getString("nama")
                        ResultScreen(result ?: "Error", nim ?: "Unknown", nama ?: "Unknown")
                    }
                }
            }
        }
    }
}

@Composable
fun InputScreen(navController: NavHostController) {
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var selectedOperation by remember { mutableStateOf("+") }
    var result by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input for first number
        TextField(
            value = input1,
            onValueChange = { input1 = it },
            label = { Text("Angka Pertama") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input for second number
        TextField(
            value = input2,
            onValueChange = { input2 = it },
            label = { Text("Angka Kedua") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Radio buttons for operation selection
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("+", "-", "*", "/").forEach { operation ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOperation == operation,
                        onClick = { selectedOperation = operation }
                    )
                    Text(text = operation)
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hitung button
        Button(onClick = {
            val angka1 = input1.toDoubleOrNull() ?: 0.0
            val angka2 = input2.toDoubleOrNull() ?: 0.0
            result = when (selectedOperation) {
                "+" -> (angka1 + angka2).toString()
                "-" -> (angka1 - angka2).toString()
                "*" -> (angka1 * angka2).toString()
                "/" -> if (angka2 != 0.0) (angka1 / angka2).toString() else "Error (Division by Zero)"
                else -> "Invalid Operation"
            }

            navController.navigate("result_screen/$result/225150407111059/Azzahra Helga Voleta")
        }) {
            Text("Hitung")
        }
    }
}

@Composable
fun ResultScreen(result: String, nim: String, nama: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hasil: $result", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "NIM: $nim")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Nama: $nama")
    }
}



