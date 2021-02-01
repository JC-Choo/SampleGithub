package dev.chu.githubsample

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.chu.chulibrary.core.BaseApplication
import dev.chu.githubsample.di.DaggerAppComponent

class MainApplication : BaseApplication() {

    companion object {
        fun get(): MainApplication = getInstance() as MainApplication
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}