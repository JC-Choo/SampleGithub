package dev.chu.chulibrary

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.toast(message: String, duration: Int = 0) =
    Toast.makeText(this, message, duration).show()

fun AppCompatActivity.toast(@StringRes resId: Int, duration: Int = 0) =
    Toast.makeText(this, resId, duration).show()