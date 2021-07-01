package dev.chu.githubsample.core

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.telephony.TelephonyManager
import android.util.TypedValue
import android.view.WindowManager
import androidx.core.content.ContextCompat
import dev.chu.githubsample.util.extensions.isUpAndroid23M
import dev.chu.githubsample.util.extensions.isUpAndroid28P

/**
 * Application Level 단 주요 환경 정보 제공 class
 */
class AppEnvInfo(
    private val context: Context
) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    val packageName: String
        get() = context.packageName

    val version: String
        get() = try {
            context.packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: Exception) {
            "1.0.0"
        }

    @Suppress("DEPRECATION")
    val versionCode: Long
        get() = try {
            if (isUpAndroid28P()) {
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
        context.display?.getRealSize(size)
        Pair(size.x, size.y)
    }

    val screenDpSize: Pair<Int, Int> by lazy {
        val metrics = context.resources.displayMetrics
        val dpWidth = (metrics.widthPixels / metrics.density).toInt()
        val dpHeight = (metrics.heightPixels / metrics.density).toInt()
        Pair(dpWidth, dpHeight)
    }

    val rotation: Int
        get() = context.display?.rotation ?: 0

    fun dpToPx(dp: Float): Int {
        val r: Resources = context.resources
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics)
        return px.toInt()
    }

    fun getNetworkCountry(): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.networkCountryIso
    }

    fun getRequiredPermissions(vararg permissions: String): Array<String> {
        val requiredPermissions = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                requiredPermissions.add(permission)
            }
        }
        return requiredPermissions.toTypedArray()
    }

    fun hasPermission(vararg permissions: String): Boolean {
        if (!isUpAndroid23M())
            return true

        val requestedPermissions: Array<String> = getRequiredPermissions(*permissions)
        return requestedPermissions.isEmpty()
    }

    // region [폴더 관련]
//    fun getDataFolder(preferExternal: Boolean): File? =
//        StorageUtils.getDataDirectory(context, preferExternal)
//
//    fun getCacheFolder(preferExternal: Boolean): File? =
//        StorageUtils.getCacheDirectory(context, preferExternal)
//
//    fun clearFolder(cacheFolder: File?): Boolean {
//        try {
//            if (cacheFolder != null && cacheFolder.isDirectory) {
//                return deleteFolder(cacheFolder)
//            }
//        } catch (e: Exception) {
//            Logger.safeThrow(e)
//        }
//        return false
//    }
//
//    fun deleteFolder(dir: File?): Boolean {
//        if (dir != null && dir.isDirectory) {
//            val children = dir.list()
//            if (children != null) {
//                for (i in 0..children.size) {
//                    val success: Boolean = deleteFolder(File(dir, children[i]))
//                    if (!success) {
//                        return false
//                    }
//                }
//            }
//        }
//        return dir?.delete() ?: false
//    }
    // endregion
}