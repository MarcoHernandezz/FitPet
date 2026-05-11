package com.example.fitpet

import android.os.SystemClock

object NavigationUtils {
    private var lastClickTime: Long = 0
    private const val DEBOUNCE_TIME = 500L

    fun canNavigate(): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        return if (currentTime - lastClickTime > DEBOUNCE_TIME) {
            lastClickTime = currentTime
            true
        } else false
    }
}
