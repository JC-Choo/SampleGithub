package dev.chu.chulibrary.util.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.toast(message: String, duration: Int = 0) =
    Toast.makeText(this, message, duration).show()

fun Context.toast(@StringRes resId: Int, duration: Int = 0) =
    Toast.makeText(this, resId, duration).show()

fun Context.getDrawableById(@DrawableRes resId: Int): Drawable? =
    ContextCompat.getDrawable(this, resId)

fun Context.getColorById(@ColorRes resId: Int): Int =
    ContextCompat.getColor(this, resId)