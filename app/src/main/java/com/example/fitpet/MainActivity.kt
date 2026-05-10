package com.example.fitpet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitpet.ui.theme.FitPetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitPetTheme {
                FitPetApp()
            }
        }
    }
}

@Composable
fun FitPetApp() {
    val navController = rememberNavController()
    // Estado compartido a nivel de App para que persista entre pantallas
    var stepsToday by rememberSaveable { mutableIntStateOf(3200) }
    val stepGoal = 10000

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    stepsToday = stepsToday,
                    onStepsChanged = { stepsToday = it },
                    onNavigateToStats = { navController.navigate("stats") },
                    onNavigateToInfo = { navController.navigate("info") }
                )
            }
            composable("stats") {
                StatsScreen(
                    stepsToday = stepsToday,
                    stepGoal = stepGoal,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("info") {
                InfoScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
