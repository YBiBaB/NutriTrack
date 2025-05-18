package com.fit2081.fit2081a2.utils

import android.content.Context

object UserSessionManager {
    private const val PREF_NAME = "user_session"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_LOGIN_TIME = "login_time"
    private const val SESSION_DURATION_MS = 3 * 60 * 60 * 1000L // 3 hours

    fun saveUserSession(context: Context, userId: Int) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(KEY_USER_ID, userId)
        editor.putLong(KEY_LOGIN_TIME, System.currentTimeMillis())
        editor.apply()
    }

    fun getLoggedInUserId(context: Context): Int? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val savedTime = prefs.getLong(KEY_LOGIN_TIME, -1)
        val currentTime = System.currentTimeMillis()

        return if (savedTime != -1L && currentTime - savedTime <= SESSION_DURATION_MS) {
            prefs.getInt(KEY_USER_ID, -1).takeIf { it != -1 }
        } else {
            null // Session expired or not logged in
        }
    }

    fun isSessionExpired(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val savedTime = prefs.getLong(KEY_LOGIN_TIME, -1)
        return savedTime == -1L || System.currentTimeMillis() - savedTime > SESSION_DURATION_MS
    }

    fun clearSession(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}