package dev.chu.chulibrary.core

import androidx.annotation.CallSuper
import dagger.android.DaggerApplication

interface BaseAppEssential {
    val appEnvInfo: AppEnvInfo
}

/**
 * 공통 라이브러리를 위한 BaseApplication
 */
abstract class BaseApplication: DaggerApplication() {

    companion object {
        @Volatile
        private lateinit var instance: BaseApplication

        @JvmStatic
        fun getInstance(): BaseApplication = instance
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}