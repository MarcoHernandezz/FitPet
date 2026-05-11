package com.example.fitpet

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fitpet_prefs")

data class UserData(
    val stepsToday: Int, 
    val petName: String, 
    val stepGoal: Int,
    val lastDate: String
)

class FitPetDataStore(private val context: Context) {

    companion object {
        val STEPS_KEY = intPreferencesKey("steps_today")
        val PET_NAME_KEY = stringPreferencesKey("pet_name")
        val STEP_GOAL_KEY = intPreferencesKey("step_goal")
        val LAST_DATE_KEY = stringPreferencesKey("last_date")
    }

    val userDataFlow: Flow<UserData> = context.dataStore.data.map { preferences ->
        val rawGoal = preferences[STEP_GOAL_KEY] ?: 10000
        UserData(
            stepsToday = preferences[STEPS_KEY] ?: 0,
            petName = preferences[PET_NAME_KEY] ?: "Fitty",
            stepGoal = if (rawGoal > 0) rawGoal else 10000,
            lastDate = preferences[LAST_DATE_KEY] ?: ""
        )
    }

    suspend fun saveSteps(steps: Int) {
        context.dataStore.edit { preferences ->
            preferences[STEPS_KEY] = steps
        }
    }

    suspend fun resetDailySteps(currentDate: String) {
        context.dataStore.edit { preferences ->
            preferences[STEPS_KEY] = 0
            preferences[LAST_DATE_KEY] = currentDate
        }
    }

    suspend fun saveSettings(name: String, goal: Int) {
        context.dataStore.edit { preferences ->
            preferences[PET_NAME_KEY] = name
            if (goal > 0) preferences[STEP_GOAL_KEY] = goal
        }
    }
}
