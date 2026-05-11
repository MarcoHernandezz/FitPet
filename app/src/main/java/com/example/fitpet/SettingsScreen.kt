package com.example.fitpet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
    onShowMessage: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nameInput by remember { mutableStateOf(viewModel.petName) }
    var goalInput by remember { mutableStateOf(viewModel.stepGoal.toString()) }
    var showResetDialog by remember { mutableStateOf(false) }

    val isInputValid = nameInput.isNotBlank() && (goalInput.toIntOrNull() ?: 0) > 0
    val scrollState = rememberScrollState()

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reiniciar progreso") },
            text = { Text("¿Estás seguro de que quieres volver los pasos a cero? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetProgress()
                    onShowMessage("Progreso reiniciado")
                    showResetDialog = false
                }) {
                    Text("Reiniciar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
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

        Button(
            onClick = {
                val goal = goalInput.toIntOrNull() ?: 10000
                viewModel.updateSettings(nameInput, goal)
                onShowMessage("Cambios guardados correctamente")
                onBack()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isInputValid
        ) {
            Text("Guardar Cambios")
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Sección Modo Demo
        Text(
            text = "Modo Demo / Herramientas",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilledTonalButton(
                onClick = { 
                    viewModel.add1000Steps() 
                    onShowMessage("Se agregaron 1000 pasos")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("+1000")
            }
            FilledTonalButton(
                onClick = { 
                    viewModel.completeGoal() 
                    onShowMessage("Meta completada")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Meta")
            }
        }

        OutlinedButton(
            onClick = { showResetDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Reiniciar pasos a 0")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
