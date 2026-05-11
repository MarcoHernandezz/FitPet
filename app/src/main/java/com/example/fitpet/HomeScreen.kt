package com.example.fitpet

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    viewModel: FitPetViewModel,
    onShowMessage: (String) -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToInfo: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera con navegación
        HeaderSection(onNavigateToStats, onNavigateToInfo, onNavigateToSettings)
        
        Spacer(modifier = Modifier.height(16.dp))

        if (isLandscape) {
            // Layout Horizontal: Dos columnas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    PetImage(viewModel.petImageRes)
                }
                Column(
                    modifier = Modifier.weight(1.2f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PetInfo(viewModel)
                    ProgressSection(viewModel)
                    CaminarButton(onClick = { 
                        viewModel.addSteps() 
                        onShowMessage("¡Se sumaron 100 pasos!")
                    })
                }
            }
        } else {
            // Layout Vertical
            PetCard(viewModel)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = viewModel.motivationalMessage,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(32.dp))
            CaminarButton(onClick = { 
                viewModel.addSteps() 
                onShowMessage("¡Se sumaron 100 pasos!")
            })
        }
    }
}

@Composable
fun HeaderSection(onStats: () -> Unit, onInfo: () -> Unit, onSettings: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "FitPet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Row {
            TextButton(onClick = onStats) { Text("Stats") }
            TextButton(onClick = onInfo) { Text("Info") }
            TextButton(onClick = onSettings) { Text("Ajustes") }
        }
    }
}

@Composable
fun PetImage(resId: Int) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = null,
        modifier = Modifier.size(180.dp)
    )
}

@Composable
fun PetInfo(viewModel: FitPetViewModel) {
    Column {
        Text(text = viewModel.petName, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(text = viewModel.petStatus, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ProgressSection(viewModel: FitPetViewModel) {
    Column {
        Text(text = "Pasos: ${viewModel.stepsToday} / ${viewModel.stepGoal}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { viewModel.progress },
            modifier = Modifier.fillMaxWidth().height(12.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Composable
fun PetCard(viewModel: FitPetViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PetImage(viewModel.petImageRes)
            Spacer(modifier = Modifier.height(16.dp))
            PetInfo(viewModel)
            Spacer(modifier = Modifier.height(24.dp))
            ProgressSection(viewModel)
        }
    }
}

@Composable
fun CaminarButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp)
    ) {
        Text("Caminar (+100 pasos)")
    }
}
