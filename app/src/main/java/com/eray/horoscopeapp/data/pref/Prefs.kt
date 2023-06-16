package com.eray.horoscopeapp.data.pref

import kotlinx.coroutines.flow.Flow

interface Prefs {
    fun getSharedString(prefKey: String): Flow<String>
    suspend fun setSharedString(prefKey: String, prefValue: String)

    fun getSharedBoolean(prefKey: String): Flow<Boolean>
    suspend fun setSharedBoolean(prefKey: String, prefValue: Boolean)

    suspend fun clearAll()
}