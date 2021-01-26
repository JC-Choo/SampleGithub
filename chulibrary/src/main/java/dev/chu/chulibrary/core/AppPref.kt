package dev.chu.chulibrary.core

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dev.chu.chulibrary.baseson.Baseson
import dev.chu.chulibrary.log.Logger

class AppPref(
    private val context: Context
) {

    companion object {
        private const val PREFERENCE_FILE_NAME = "pref_file"
    }

    protected val pref: SharedPreferences by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

            EncryptedSharedPreferences.create(
                context.packageName+ PREFERENCE_FILE_NAME,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            context.getSharedPreferences(getPreferenceName(context), Context.MODE_PRIVATE)
        }
    }

    protected fun getPreferenceName(context: Context): String =
        context.applicationInfo.className

    fun put(key: String, obj: Any) {
        val prefEditor = pref.edit()

        when(obj) {
            is String -> prefEditor.putString(key, obj)
            is Boolean -> prefEditor.putBoolean(key, obj)
            is Int -> prefEditor.putInt(key, obj)
            is Long -> prefEditor.putLong(key, obj)
            else -> {
                val jsonObj: String = Baseson.toJson(obj)
                prefEditor.putString(key, jsonObj)
            }
        }

        prefEditor.apply()
    }

    fun getString(key: String, defaultValue: String? = null): String? =
        pref.getString(key, defaultValue ?: "")

    fun getBoolean(key: String, defaultValue: Boolean = false) =
        pref.getBoolean(key, defaultValue)

    fun getInt(key: String, defaultValue: Int = 0) =
        pref.getInt(key, defaultValue)

    fun getLong(key: String, defaultValue: Long = 0L) =
        pref.getLong(key, defaultValue)

    inline fun <reified T> getObject(key: String): T? {
        var returnValue: T? = null
        try {
            val jsonStr = getString(key)
            if (jsonStr != null) {
                returnValue = Baseson.fromJson(jsonStr, T::class.java)
            }
        } catch (e: Exception) {
            Logger.safeThrow(e)
        }

        return returnValue
    }

    fun remove(key: String) {
        val prefEditor = pref.edit()
        prefEditor.remove(key)
        prefEditor.apply()
    }
}
