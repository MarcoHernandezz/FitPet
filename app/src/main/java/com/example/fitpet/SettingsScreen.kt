package com.example.fitpet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    viewModel: FitPetViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nameInput by remember { mutableStateOf(viewModel.petName) }
    var goalInput by remember { mutableStateOf(viewModel.stepGoal.toString()) }

    // Validación local: el nombre no puede estar vacío y la meta debe ser > 0
    val isInputValid = nameInput.isNotBlank() && (goalInput.toIntOrNull() ?: 0) > 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Ajustes",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Nombre de la mascota") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = nameInput.isBlank()
        )

        OutlinedTextField(
            value = goalInput,
            onValueChange = { if (it.all { char -> char.isDigit() }) goalInput = it },
            label = { Text("Meta diaria de pasos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = (goalInput.toIntOrNull() ?: 0) <= 0
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val goal = goalInput.toIntOrNull() ?: 10000
                viewModel.updateSettings(nameInput, goal)
                onBack()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isInputValid
        ) {
            Text("Guardar Cambios")
        }

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}
