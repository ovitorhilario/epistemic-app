package br.com.epistemic.app.presentation.activities.main.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.epistemic.app.di.modules.CoroutineDispatcherIO
import br.com.epistemic.app.domain.repository.LANGUAGES
import br.com.epistemic.app.domain.repository.UserPreferences
import br.com.epistemic.app.domain.usecases.GetUserPrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserPrefsUseCase: GetUserPrefsUseCase,
    @CoroutineDispatcherIO private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _userPrefs = MutableLiveData<UserPreferences>()
    val userPrefs: LiveData<UserPreferences> = _userPrefs

    init {
        fetchPrefs()
    }

    private fun fetchPrefs() {
        viewModelScope.launch(dispatcher) {
            try {
                _userPrefs.postValue(getUserPrefsUseCase().first())
            } catch (e: IOException) {
                _userPrefs.postValue(UserPreferences(languageTag = LANGUAGES.PT_BR.tag))
            }
        }
    }
}