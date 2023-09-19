package br.com.epistemic.app.commom.flags

sealed class Event {
    object Success: Event()
    data class Failure(val error: InvalidEntry): Event()
}