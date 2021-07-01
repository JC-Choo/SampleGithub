package dev.chu.githubsample.util.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, resId, duration).show()