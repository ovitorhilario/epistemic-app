package br.com.epistemic.app.presentation.activities.main.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.epistemic.app.commom.ext.isValidEmail
import br.com.epistemic.app.commom.ext.isValidPassword
import br.com.epistemic.app.commom.flags.Event
import br.com.epistemic.app.commom.flags.InvalidEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val _events = MutableLiveData<Event>()
    val events: LiveData<Event> = _events

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        when {
            name.isEmpty() -> _events.value = Event.Failure(InvalidEntry.Empty)
            !email.isValidEmail() -> _events.value = Event.Failure(InvalidEntry.Email)
            !password.isValidPassword() -> _events.value = Event.Failure(InvalidEntry.Password)
            password != confirmPassword -> _events.value = Event.Failure(InvalidEntry.PasswordNotMatch)
            else -> _events.value = Event.Success
        }
    }

}