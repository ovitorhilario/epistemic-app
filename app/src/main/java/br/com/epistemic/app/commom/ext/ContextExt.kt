package br.com.epistemic.app.commom.ext

import android.content.Context
import android.widget.Toast

fun Context.showSuccess(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showError(error: String) {
    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
}

fun Context.showInfo(info: String) {
    Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
}