package dev.chu.chulibrary.util.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

fun Context.toast(message: String, duration: Int = 0) =
    Toast.makeText(this, message, duration).show()

fun Context.toast(@StringRes resId: Int, duration: Int = 0) =
    Toast.makeText(this, resId, duration).show()

/**
 * Context 로부터 AppCompatActivity 를 찾기 위한 fun
 */
fun Context.findActivity(): AppCompatActivity? {
    var tempContext = this

    while (tempContext is ContextWrapper) {
        if (tempContext is AppCompatActivity) {
            return tempContext
        }
        tempContext = tempContext.baseContext
    }
    return null
}

/**
 * Drawable 과 Color 를 버전 분기를 통해 가져오기 위한 fun
 */
@SuppressLint("UseCompatLoadingForDrawables")
fun Context.getDrawableById(@DrawableRes res: Int): Drawable = getDrawable(res)!!

@Suppress("DEPRECATION")
fun Context.getColorById(@ColorRes res: Int): Int =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) getColor(res) else resources.getColor(res)