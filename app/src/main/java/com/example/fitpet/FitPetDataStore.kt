package com.example.fitpet

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fitpet_prefs")

class FitPetDataStore(private val context: Context) {

    companion object {
        val STEPS_KEY = intPreferencesKey("steps_today")
    }

    val stepsFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[STEPS_KEY] ?: 3200
    }

    suspend fun saveSteps(steps: Int) {
        context.dataStore.edit { preferences ->
            preferences[STEPS_KEY] = steps
        }
    }
}
