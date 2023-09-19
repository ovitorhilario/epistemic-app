package br.com.epistemic.app.presentation.activities.main.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.epistemic.app.commom.ext.isValidEmail
import br.com.epistemic.app.commom.ext.isValidPassword
import br.com.epistemic.app.commom.flags.Event
import br.com.epistemic.app.commom.flags.InvalidEntry
import br.com.epistemic.app.commom.singleton.AppConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val _events = MutableLiveData<Event>()
    val events: LiveData<Event> = _events

    private val _contTryLogin = MutableLiveData(0)

    fun login(email: String, password: String) {
        when {
            !email.isValidEmail() -> _events.value = Event.Failure(InvalidEntry.Email)
            !password.isValidPassword() -> _events.value = Event.Failure(InvalidEntry.Password)
            (_contTryLogin.value ?: 0) >= 3 -> _events.value = Event.Failure(InvalidEntry.AuthExceedLimit)
            else -> {
                if (email == AppConfig.email && password == AppConfig.password) {
                    _contTryLogin.value = 0
                    _events.value = Event.Success
                } else {
                    _contTryLogin.value = (_contTryLogin.value ?: 0) + 1
                }
            }
        }
    }
}