package com.example.nefrovida.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(
    private val context: Context,
) {
    companion object {
        val USER_ID = stringPreferencesKey("user_id")
    }

    suspend fun saveUserId(id: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = id
        }
    }

    fun getUserId(): Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[USER_ID]
        }
}
