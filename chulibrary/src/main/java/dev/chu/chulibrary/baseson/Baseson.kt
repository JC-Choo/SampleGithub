package dev.chu.chulibrary.baseson

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * json de/serialize 를 위한 [Gson] wrapper object
 *
 */
object Baseson {
    val gson: Gson by lazy {
        GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create()
    }

    fun toJson(src: Any): String = gson.toJson(src)

    fun <T> fromJson(json: String?, clz: Class<T>): T? = gson.fromJson(json, clz)

    inline fun <reified T> fromJson(json: String?): T? = fromJson(json, T::class.java)
}