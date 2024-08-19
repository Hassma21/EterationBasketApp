package eteration.muhammed.basketapp.core.persistence

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Persistence @Inject constructor(@ApplicationContext val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "app_setting.ds")


    suspend fun save(key: String, value: Int) {
        context.dataStore.edit { settings ->
            val prefKey = intPreferencesKey(key)
            settings[prefKey] = value
        }
    }


    suspend fun save(key: String, value: Long) {
        context.dataStore.edit { settings ->
            val prefKey = longPreferencesKey(key)
            settings[prefKey] = value
        }
    }


    fun read(key: String, defaultValue: Int): Flow<Int> {
        val prefKey = intPreferencesKey(key)
        return context.dataStore.data.map { it[prefKey] ?: defaultValue }
    }


    fun read(key: String, defaultValue: Long): Flow<Long> {
        val prefKey = longPreferencesKey(key)
        return context.dataStore.data.map { it[prefKey] ?: defaultValue }
    }
}