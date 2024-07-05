package com.cleartab.cleartab.utils

import android.content.Context

object SharedPreferencesUtil {

    private const val PREFERENCES_FILE_NAME = "user_prefs"

    fun saveIds(context: Context, idNameToSave: String, idToSave: Long) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(idNameToSave, idToSave)
        editor.apply()
    }

    fun getIDs(context: Context, idToRetrieve: String): Long {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(idToRetrieve, -1)
    }
}