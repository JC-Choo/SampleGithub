package dev.chu.githubsample.util.extensions

import android.os.Build

fun isUpAndroid23M() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
fun isUpAndroid28P() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
fun isUpAndroid30R() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R