package br.com.epistemic.app.commom.flags

sealed class InvalidEntry {
    object Email: InvalidEntry()
    object Password: InvalidEntry()
    object PasswordNotMatch: InvalidEntry()
    object Empty: InvalidEntry()
    object AuthExceedLimit: InvalidEntry()
}