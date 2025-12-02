package com.example.nefrovida.domain.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userDataStore by preferencesDataStore(name = "user_preferences")

object UserPreferencesKeys {
    val USER_ID = stringPreferencesKey("user_id")
}

class UserPreferencesRepository
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        val userIdFlow: Flow<String?> =
            context.userDataStore.data
                .map { prefs ->
                    prefs[UserPreferencesKeys.USER_ID]
                }

        suspend fun saveUserId(id: String) {
            context.userDataStore.edit { prefs ->
                prefs[UserPreferencesKeys.USER_ID] = id
            }
        }

        suspend fun clearUserId() {
            context.userDataStore.edit { prefs ->
                prefs.remove(UserPreferencesKeys.USER_ID)
            }
        }
    }
