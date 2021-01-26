package dev.chu.chulibrary.util.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String, duration: Int = 0) =
    Toast.makeText(requireContext(), message, duration).show()

fun Fragment.toast(@StringRes resId: Int, duration: Int = 0) =
    Toast.makeText(requireContext(), resId, duration).show()