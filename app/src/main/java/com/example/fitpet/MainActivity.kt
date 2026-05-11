package com.example.fitpet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitpet.ui.theme.FitPetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializamos el DataStore para persistencia
        val dataStore = FitPetDataStore(applicationContext)
        
        enableEdgeToEdge()
        setContent {
            FitPetTheme {
                // Proveemos el ViewModel con su dependencia inyectada manualmente
                val viewModel: FitPetViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return FitPetViewModel(dataStore) as T
                        }
                    }
                )
                FitPetApp(viewModel)
            }
        }
    }
}

@Composable
fun FitPetApp(viewModel: FitPetViewModel) {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToStats = {
                        if (NavigationUtils.canNavigate()) {
                            navController.navigate("stats") {
                                launchSingleTop = true
                            }
                        }
                    },
                    onNavigateToInfo = {
                        if (NavigationUtils.canNavigate()) {
                            navController.navigate("info") {
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
            composable("stats") {
                StatsScreen(
                    viewModel = viewModel,
                    onBack = {
                        if (NavigationUtils.canNavigate()) {
                            navController.popBackStack()
                        }
                    }
                )
            }
            composable("info") {
                InfoScreen(
                    onBack = {
                        if (NavigationUtils.canNavigate()) {
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
    }
}
