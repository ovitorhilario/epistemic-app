package br.com.epistemic.app.domain.repository

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

enum class LANGUAGES(val tag: String) {
    PT_BR("pt"), EN_US("en")
}

data class UserPreferences(
    val medicines: Boolean = true,
    val exercises: Boolean = true,
    val languageTag: String = LANGUAGES.PT_BR.tag,
    val notificationsPerDay: Int = 0
)

interface UserPreferencesRepository {

    val userPrefs: Flow<UserPreferences>

    suspend fun updateUserPrefs(prefs: UserPreferences)

    object PreferencesKeys {
        val USER_LANGUAGE_TAG = stringPreferencesKey("user_language_tag")
        val USER_MEDICINES = booleanPreferencesKey("user_medicines")
        val USER_EXERCISES = booleanPreferencesKey("user_exercises")
        val USER_NOTIFICATIONS_PER_DAY = intPreferencesKey("user_notifications_per_day")
    }
}
