package com.example.fitpet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: FitPetViewModel,
    onNavigateToHome: () -> Unit
) {
    // Lógica de transición: espera a que los datos estén listos y un tiempo mínimo visual
    LaunchedEffect(viewModel.isDataLoaded) {
        // Mantenemos la splash 2 segundos para que luzca el nuevo diseño
        delay(2000)
        if (viewModel.isDataLoaded) {
            onNavigateToHome()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Imagen de fondo a pantalla completa
        Image(
            painter = painterResource(id = R.drawable.splash_fitpet),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Asegura que cubra toda el área
        )

        // 2. Capa de degradado oscuro para mejorar el contraste del texto
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )

        // 3. Contenido de texto superpuesto
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "FitPet",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Tu mascota, tu salud",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Cargando experiencia...",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}
