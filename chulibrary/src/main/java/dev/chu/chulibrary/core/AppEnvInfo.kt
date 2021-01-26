package dev.chu.chulibrary.core

import android.content.Context
import android.content.pm.InstallSourceInfo
import android.content.pm.PackageInfo
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception

@Suppress("DEPRECATION")
class AppEnvInfo(
    private val context: Context
) {

    private val packageName: String
        get() = context.packageName

    private val packageInfo: PackageInfo
        get() = context.packageManager.getPackageInfo(packageName, 0)

    val version: String
        get() = try {
            packageInfo.versionName
        } catch (e: Exception) {
            "1.0.0"
        }

    val versionCode: Long
        get() = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
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

    /**
     * 처음 설치 시간
     */
    val firstDownloadTime: String by lazy {
        // utc milliseconds to formatted date
        val date = Date(packageInfo.firstInstallTime)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        formatter.format(date)
    }

    /**
     * 마지막 업데이트한 시간
     */
    val lastUpdateTime: String by lazy {
        // utc milliseconds to formatted date
        val date = Date(packageInfo.lastUpdateTime)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        formatter.format(date)
    }

    /**
     * 앱 설치 정보 -> 누가 앱을 설치했는지?
     */
    val installInfo: String by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val info : InstallSourceInfo = context.packageManager.getInstallSourceInfo(packageName)
            "versionName: ${info.initiatingPackageName}"
        } else {
            val installer : String? = context.packageManager.getInstallerPackageName(packageName)
            "versionName: $installer"
        }
    }
}