package br.com.epistemic.app.domain.usecases

import br.com.epistemic.app.domain.repository.UserPreferences
import br.com.epistemic.app.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPrefsUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<UserPreferences> {
        return repository.userPrefs
    }
}