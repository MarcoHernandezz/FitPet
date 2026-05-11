package com.example.fitpet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var isDataLoaded by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            dataStore.userDataFlow.collectLatest { data ->
                stepsToday = data.stepsToday
                petName = data.petName.ifBlank { "Fitty" }
                stepGoal = if (data.stepGoal > 0) data.stepGoal else 10000
                isDataLoaded = true
            }
        }
    }

    val progress: Float
        get() = if (isDataLoaded && stepGoal > 0) {
            (stepsToday.toFloat() / stepGoal).coerceIn(0f, 1f)
        } else {
            0f
        }

    val petStatus: String
        get() = if (!isDataLoaded) {
            "Cargando..."
        } else when {
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
        get() = if (!isDataLoaded) {
            "Preparando a tu mascota..."
        } else if (stepsToday >= stepGoal) {
            "¡Increíble! Han alcanzado la meta de hoy."
        } else {
            "¡$petName cuenta contigo para seguir moviéndose!"
        }

    fun addSteps() {
        if (!isDataLoaded) return
        viewModelScope.launch {
            dataStore.saveSteps(stepsToday + 100)
        }
    }

    fun resetProgress() {
        if (!isDataLoaded) return
        viewModelScope.launch {
            dataStore.saveSteps(0)
        }
    }

    fun add1000Steps() {
        if (!isDataLoaded) return
        viewModelScope.launch {
            dataStore.saveSteps(stepsToday + 1000)
        }
    }

    fun completeGoal() {
        if (!isDataLoaded) return
        viewModelScope.launch {
            dataStore.saveSteps(stepGoal)
        }
    }

    fun updateSettings(newName: String, newGoal: Int) {
        if (newName.isBlank()) return
        if (newGoal <= 0) return

        viewModelScope.launch {
            dataStore.saveSettings(newName, newGoal)
        }
    }
}