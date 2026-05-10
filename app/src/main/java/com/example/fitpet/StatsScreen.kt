package com.example.fitpet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StatsScreen(
    stepsToday: Int,
    stepGoal: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progressPercent = (stepsToday.toFloat() / stepGoal * 100).toInt()
    
    val (petStatus, petImageRes) = when {
        stepsToday < 2500 -> "Dormida" to R.drawable.pet_sleepy
        stepsToday < 5000 -> "Despierta" to R.drawable.pet_awake
        stepsToday < 7500 -> "Activa" to R.drawable.pet_active
        stepsToday < 10000 -> "Feliz" to R.drawable.pet_happy
        else -> "Meta cumplida" to R.drawable.pet_goal
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Estadísticas",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = petImageRes),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
                Text(text = "Estado: $petStatus", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Pasos actuales: $stepsToday", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Meta diaria: $stepGoal", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "Progreso: $progressPercent%",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
