package com.example.fitpet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    stepsToday: Int,
    onStepsChanged: (Int) -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToInfo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stepGoal = 10000

    // Lógica reactiva para determinar el estado de la mascota según los pasos
    val (petStatus, petImageRes, motivationalMessage) = when {
        stepsToday < 2500 -> Triple(
            "Dormida", 
            R.drawable.pet_sleepy, 
            "Shhh... Fitty está descansando."
        )
        stepsToday < 5000 -> Triple(
            "Despierta", 
            R.drawable.pet_awake, 
            "¡Fitty ha despertado! ¿Damos un paseo?"
        )
        stepsToday < 7500 -> Triple(
            "Activa", 
            R.drawable.pet_active, 
            "¡A Fitty le encanta moverse! ¡Sigue así!"
        )
        stepsToday < 10000 -> Triple(
            "Feliz", 
            R.drawable.pet_happy, 
            "¡Fitty está muy feliz por el ejercicio!"
        )
        else -> Triple(
            "Meta cumplida", 
            R.drawable.pet_goal, 
            "¡Increíble! Han alcanzado la meta de hoy."
        )
    }

    val progress = (stepsToday.toFloat() / stepGoal).coerceAtMost(1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título y accesos rápidos de navegación
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
                TextButton(onClick = onNavigateToStats) { Text("Stats") }
                TextButton(onClick = onNavigateToInfo) { Text("Info") }
            }
        }

        // Tarjeta Central Principal
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = petImageRes),
                    contentDescription = "Estado de Fitty",
                    modifier = Modifier.size(180.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Fitty",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = petStatus,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Pasos de hoy: $stepsToday / $stepGoal",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.outlineVariant,
                )
            }
        }

        Text(
            text = motivationalMessage,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onStepsChanged(stepsToday + 100) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Caminar (+100 pasos)",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
