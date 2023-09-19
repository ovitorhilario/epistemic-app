package br.com.epistemic.app.commom.ext

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.load(uri: String) {
    Picasso.get()
        .load(uri)
        .into(this)
}