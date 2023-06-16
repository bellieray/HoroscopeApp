package com.eray.horoscopeapp.data.pref

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val PREFERENCES_NAME = "preferences"

class PrefsImpl @Inject constructor(val context: Context) : Prefs {
    private val Context.dataStore by preferencesDataStore(
        name = PREFERENCES_NAME,
        produceMigrations = { context ->
            listOf(
                SharedPreferencesMigration(
                    context,
                    context.packageName + "_preferences"
                )
            )
        }
    )

    override fun getSharedString(prefKey: String) = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[stringPreferencesKey(prefKey)] ?: "" }

    override suspend fun setSharedString(prefKey: String, prefValue: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(prefKey)] = prefValue
        }
    }

    override fun getSharedBoolean(prefKey: String): Flow<Boolean> =
        context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { it[booleanPreferencesKey(prefKey)] ?: false }

    override suspend fun setSharedBoolean(prefKey: String, prefValue: Boolean) {
        context.dataStore.edit {
            it[booleanPreferencesKey(prefKey)] = prefValue
        }
    }

    override suspend fun clearAll(){
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}