package br.com.epistemic.app.presentation.activities.main.home.model

import android.graphics.drawable.Drawable

sealed class HomeItem {
    data class TopAppBar(val onClick: () -> Unit): HomeItem()
    data class Welcome(val name: String, val date: String): HomeItem()
    data class Actions(val list: ArrayList<Action>): HomeItem()
}

data class Action(
    val type: String,
    val description: String,
    val imageUrl: String,
    val backgroundRes: Drawable?,
    val onClick: () -> Unit
)