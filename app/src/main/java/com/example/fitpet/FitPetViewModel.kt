package com.example.fitpet

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FitPetViewModel(private val dataStore: FitPetDataStore) : ViewModel() {
    
    var stepsToday by mutableIntStateOf(0)
        private set
    
    var stepGoal by mutableIntStateOf(10000)
        private set

    var petName by mutableStateOf("Fitty")
        private set

    // Bandera para evitar cálculos con datos parciales durante el arranque
    var isDataLoaded by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            // Observamos cambios en tiempo real desde DataStore
            dataStore.userDataFlow.collectLatest { data ->
                stepsToday = data.stepsToday
                petName = data.petName
                stepGoal = data.stepGoal
                isDataLoaded = true
            }
        }
    }

    // Cálculo corregido: Evitamos división por valores erróneos y usamos coerceIn para seguridad
    val progress: Float
        get() = if (isDataLoaded && stepGoal > 0) {
            (stepsToday.toFloat() / stepGoal).coerceIn(0f, 1f)
        } else 0f

    val petStatus: String
        get() = if (!isDataLoaded) "Cargando..." else when {
            stepsToday < (stepGoal * 0.25) -> "Dormida"
            stepsToday < (stepGoal * 0.50) -> "Despierta"
            stepsToday < (stepGoal * 0.75) -> "Activa"
            stepsToday < stepGoal -> "Feliz"
            else -> "Meta cumplida"
        }

    val petImageRes: Int
        get() = when {
            !isDataLoaded || stepsToday < (stepGoal * 0.25) -> R.drawable.pet_sleepy
            stepsToday < (stepGoal * 0.50) -> R.drawable.pet_awake
            stepsToday < (stepGoal * 0.75) -> R.drawable.pet_active
            stepsToday < stepGoal -> R.drawable.pet_happy
            else -> R.drawable.pet_goal
        }

    val motivationalMessage: String
        get() = if (!isDataLoaded) "Preparando a tu mascota..."
                else if (stepsToday >= stepGoal) "¡Increíble! Han alcanzado la meta de hoy."
                else "¡$petName cuenta contigo para seguir moviéndose!"

    fun addSteps() {
        if (!isDataLoaded) return
        viewModelScope.launch {
            dataStore.saveSteps(stepsToday + 100)
        }
    }

    fun updateSettings(newName: String, newGoal: Int) {
        // Validación extra de seguridad en el ViewModel
        if (newGoal <= 0 || newName.isBlank()) return
        viewModelScope.launch {
            dataStore.saveSettings(newName, newGoal)
        }
    }
}
