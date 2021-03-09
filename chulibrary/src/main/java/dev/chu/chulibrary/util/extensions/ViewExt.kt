package dev.chu.chulibrary.util.extensions

import android.view.View

fun View.click(block: (View) -> Unit) {
    this.setOnClickListener(block)
}