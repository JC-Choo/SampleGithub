package dev.chu.chulibrary.core

import androidx.annotation.CallSuper
import dagger.android.DaggerApplication

/**
 * Application 레벨의 Utility 성 컨트롤 interface
 */
interface BaseAppEssential {
    val appEnvInfo: AppEnvInfo
    val appPref: AppPref
}

/**
 * 공통 라이브러리를 위한 BaseApplication
 */
abstract class BaseApplication: DaggerApplication(), BaseAppEssential {

    override val appEnvInfo: AppEnvInfo by lazy { AppEnvInfo(this) }
    override val appPref: AppPref by lazy { AppPref(this) }

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