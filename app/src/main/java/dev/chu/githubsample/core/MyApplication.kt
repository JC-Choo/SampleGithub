package dev.chu.githubsample.core

import androidx.annotation.CallSuper
import dagger.android.support.DaggerApplication

interface MyAppEssential {
    val appEnvInfo: AppEnvInfo
    val appPref: AppPref
}

abstract class MyApplication : DaggerApplication(), MyAppEssential {

    override val appEnvInfo: AppEnvInfo by lazy { AppEnvInfo(this) }
    override val appPref: AppPref by lazy { AppPref(this) }

    companion object {
        @Volatile
        private lateinit var instance: MyApplication

        @JvmStatic
        fun getInstance(): MyApplication = instance
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}