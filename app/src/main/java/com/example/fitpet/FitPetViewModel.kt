package com.example.fitpet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FitPetViewModel(private val dataStore: FitPetDataStore) : ViewModel() {
    
    var stepsToday by mutableIntStateOf(0)
        private set
    
    val stepGoal = 10000
    val petName = "Fitty"

    init {
        viewModelScope.launch {
            stepsToday = dataStore.stepsFlow.first()
        }
    }

    val progress: Float
        get() = (stepsToday.toFloat() / stepGoal).coerceAtMost(1f)

    val petStatus: String
        get() = when {
            stepsToday < 2500 -> "Dormida"
            stepsToday < 5000 -> "Despierta"
            stepsToday < 7500 -> "Activa"
            stepsToday < 10000 -> "Feliz"
            else -> "Meta cumplida"
        }

    val petImageRes: Int
        get() = when {
            stepsToday < 2500 -> R.drawable.pet_sleepy
            stepsToday < 5000 -> R.drawable.pet_awake
            stepsToday < 7500 -> R.drawable.pet_active
            stepsToday < 10000 -> R.drawable.pet_happy
            else -> R.drawable.pet_goal
        }

    val motivationalMessage: String
        get() = when {
            stepsToday < 2500 -> "Shhh... Fitty está descansando."
            stepsToday < 5000 -> "¡Fitty ha despertado! ¿Damos un paseo?"
            stepsToday < 7500 -> "¡A Fitty le encanta moverse! ¡Sigue así!"
            stepsToday < 10000 -> "¡Fitty está muy feliz por el ejercicio!"
            else -> "¡Increíble! Han alcanzado la meta de hoy."
        }

    fun addSteps() {
        stepsToday += 100
        viewModelScope.launch {
            dataStore.saveSteps(stepsToday)
        }
    }
}
