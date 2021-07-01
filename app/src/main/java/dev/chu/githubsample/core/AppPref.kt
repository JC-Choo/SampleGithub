package dev.chu.githubsample.core

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dev.chu.githubsample.util.extensions.isUpAndroid23M

class AppPref(
    private val context: Context
) {

    companion object {
        private const val PREFERENCE_FILE_NAME = "_app_pref_file"
    }

    protected fun getPreferenceName(context: Context): String {
        return context.applicationInfo.className
    }

    protected val pref: SharedPreferences by lazy {
        if (isUpAndroid23M()) {
            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

            EncryptedSharedPreferences.create(
                context.packageName + PREFERENCE_FILE_NAME,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            context.getSharedPreferences(getPreferenceName(context), Context.MODE_PRIVATE)
        }
    }

    fun getString(key: String): String {
        return getString(key, "")
    }

    fun getString(key: String, defaultValue: String): String {
        return pref.getString(key, defaultValue) ?: defaultValue
    }

    fun put(key: String, value: String) {
        val prefEditor: SharedPreferences.Editor = pref.edit()
        prefEditor.putString(key, value)
        prefEditor.apply()
    }

//    inline fun <reified T> getObject(key: String): T? {
//        var returnValue: T? = null
//        try {
//            val jsonString = getString(key)
//            if (jsonString != null) {
//                returnValue = Arkson.fromJson(jsonString, T::class.java)
//            }
//        } catch (e: Exception) {
//            Logger.safeThrow(e)
//        }
//        return returnValue
//    }
//
//    fun put(key: String, obj: Any) {
//        val prefEditor: SharedPreferences.Editor = pref.edit()
//        val jsonObj: String? = Arkson.toJson(obj)
//        prefEditor.putString(key, jsonObj)
//        prefEditor.apply()
//    }

    fun getBoolean(key: String): Boolean = getBoolean(key, false)

    fun getBoolean(key: String, defValue: Boolean): Boolean = pref.getBoolean(key, defValue)

    fun put(key: String, value: Boolean) {
        val prefEditor: SharedPreferences.Editor = pref.edit()
        prefEditor.putBoolean(key, value)
        prefEditor.apply()
    }

    fun getInt(key: String, defValue: Int = 0): Int = pref.getInt(key, defValue)

    fun put(key: String, value: Int) {
        val prefEditor: SharedPreferences.Editor = pref.edit()
        prefEditor.putInt(key, value)
        prefEditor.apply()
    }

    fun getLong(key: String): Long = pref.getLong(key, 0)

    fun put(key: String, value: Long) {
        val prefEditor: SharedPreferences.Editor = pref.edit()
        prefEditor.putLong(key, value)
        prefEditor.apply()
    }

    fun remove(key: String) {
        val prefEditor: SharedPreferences.Editor = pref.edit()
        prefEditor.remove(key)
        prefEditor.apply()
    }
}