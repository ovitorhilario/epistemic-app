package br.com.epistemic.app.presentation.activities.main.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.epistemic.app.di.modules.CoroutineDispatcherIO
import br.com.epistemic.app.domain.repository.UserPreferences
import br.com.epistemic.app.domain.usecases.GetUserPrefsUseCase
import br.com.epistemic.app.domain.usecases.UpdateUserPrefsUseCase
import br.com.epistemic.app.presentation.receivers.contract.AlarmScheduler
import br.com.epistemic.app.presentation.receivers.model.AlarmItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserPrefsUseCase: GetUserPrefsUseCase,
    private val updateUserPrefsUseCase: UpdateUserPrefsUseCase,
    private val alarmScheduler: AlarmScheduler,
    @CoroutineDispatcherIO private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _userPrefs = MutableLiveData<UserPreferences>()
    val userPrefs: LiveData<UserPreferences> = _userPrefs

    private val _messages = MutableLiveData<String>()
    val messages: LiveData<String> = _messages

    init {
        fetchPrefs()
    }

    private fun fetchPrefs() {
        viewModelScope.launch(dispatcher) {
            try {
                _userPrefs.postValue(getUserPrefsUseCase().first())
            } catch (e: IOException) {
                // TODO Handle IO Exception
            }
        }
    }

    private fun updatePrefs(newPrefs: UserPreferences) {
        viewModelScope.launch(dispatcher) {
            updateUserPrefsUseCase(newPrefs)
            fetchPrefs()
        }
    }

    fun toggleMedicine() {
        _userPrefs.value?.let { prefs ->
            updatePrefs(prefs.copy(medicines = !prefs.medicines))
        }
    }

    fun toggleExercises() {
        _userPrefs.value?.let { prefs ->
            updatePrefs(prefs.copy(exercises = !prefs.exercises))
        }
    }

    fun updateLanguage(tag: String): Boolean {
        return _userPrefs.value?.let { prefs ->
            if (prefs.languageTag != tag) {
                updatePrefs(prefs.copy(languageTag = tag))
                true
            } else false
        } ?: false
    }

    fun updateNotifications(qnt: Int) {
        _userPrefs.value?.let { prefs ->
            if (prefs.notificationsPerDay != qnt) {
                if (prefs.notificationsPerDay > 0)  {
                    alarmScheduler.cancel(prefs.notificationsPerDay)
                }
                alarmScheduler.schedule(AlarmItem(qnt))?.let {
                    _messages.postValue(it)
                }
                updatePrefs(prefs.copy(notificationsPerDay = qnt))
            }
        }
    }
}