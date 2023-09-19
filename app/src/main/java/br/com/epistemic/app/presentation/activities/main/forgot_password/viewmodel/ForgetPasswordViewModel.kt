package br.com.epistemic.app.presentation.activities.main.forgot_password.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.epistemic.app.commom.ext.isValidEmail
import br.com.epistemic.app.commom.flags.Event
import br.com.epistemic.app.commom.flags.InvalidEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor() : ViewModel() {

    private val _events = MutableLiveData<Event>()
    val events: LiveData<Event> = _events

    fun sendRecoveryEmail(email: String) {
        when {
            !email.isValidEmail() -> _events.value = Event.Failure(InvalidEntry.Email)
            else -> _events.value = Event.Success
        }
    }
}