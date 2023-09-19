package br.com.epistemic.app.commom.ext

const val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

fun String.isValidEmail(): Boolean {
    return this.matches(emailRegex.toRegex())
}

fun String.isValidPassword(): Boolean {
    return this.length >=6
}