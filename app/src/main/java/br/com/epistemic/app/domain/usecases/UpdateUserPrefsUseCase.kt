package br.com.epistemic.app.domain.usecases

import br.com.epistemic.app.domain.repository.UserPreferences
import br.com.epistemic.app.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class UpdateUserPrefsUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(userPrefs: UserPreferences) {
        repository.updateUserPrefs(userPrefs)
    }
}