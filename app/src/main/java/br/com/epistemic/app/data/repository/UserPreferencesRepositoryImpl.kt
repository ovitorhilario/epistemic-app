package br.com.epistemic.app.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import br.com.epistemic.app.domain.repository.LANGUAGES
import br.com.epistemic.app.domain.repository.UserPreferences
import br.com.epistemic.app.domain.repository.UserPreferencesRepository
import br.com.epistemic.app.domain.repository.UserPreferencesRepository.PreferencesKeys.USER_EXERCISES
import br.com.epistemic.app.domain.repository.UserPreferencesRepository.PreferencesKeys.USER_LANGUAGE_TAG
import br.com.epistemic.app.domain.repository.UserPreferencesRepository.PreferencesKeys.USER_MEDICINES
import br.com.epistemic.app.domain.repository.UserPreferencesRepository.PreferencesKeys.USER_NOTIFICATIONS_PER_DAY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {

    override val userPrefs: Flow<UserPreferences> = dataStore.data.map {
        preferences -> UserPreferences(
            medicines = preferences[USER_MEDICINES] ?: true,
            exercises = preferences[USER_EXERCISES] ?: true,
            languageTag = preferences[USER_LANGUAGE_TAG] ?: LANGUAGES.PT_BR.tag,
            notificationsPerDay = preferences[USER_NOTIFICATIONS_PER_DAY] ?: 0
        )
    }

    override suspend fun updateUserPrefs(prefs: UserPreferences) {
        dataStore.edit { preferences ->
            preferences[USER_MEDICINES] = prefs.medicines
            preferences[USER_EXERCISES] = prefs.exercises
            preferences[USER_LANGUAGE_TAG] = prefs.languageTag
            preferences[USER_NOTIFICATIONS_PER_DAY] = prefs.notificationsPerDay
        }
    }
}