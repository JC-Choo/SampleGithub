package dev.chu.chulibrary.core

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import kotlin.Exception

@Suppress("DEPRECATION")
class AppEnvInfo(
    private val context: Context
) {

    private val packageName: String
        get() = context.packageName

    val version: String
        get() = try {
            context.packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: Exception) {
            "1.0.0"
        }

    val versionCode: Long
        get() = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                context.packageManager.getPackageInfo(packageName, 0).longVersionCode
            } else {
                context.packageManager.getPackageInfo(packageName, 0).versionCode.toLong()
            }
        } catch (e: Exception) {
            1
        }

    val deviceName: String
        get() = Build.MODEL

    val userAgent: String by lazy {
        String.format("MobileApp/1.0 (Android; $version; $packageName; $deviceName)")
    }

    val screenSize: Pair<Int, Int> by lazy {
        val size = Point()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(size)
        Pair(size.x, size.y)
    }

    val screenDpSize: Pair<Int, Int> by lazy {
        val metrics = context.resources.displayMetrics
        val dpWidth = (metrics.widthPixels / metrics.density).toInt()
        val dpHeight = (metrics.heightPixels / metrics.density).toInt()
        Pair(dpWidth, dpHeight)
    }
}