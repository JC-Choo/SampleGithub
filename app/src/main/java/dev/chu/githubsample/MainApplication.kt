package dev.chu.githubsample

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import dev.chu.githubsample.core.MyApplication

class MainApplication: MyApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        TODO("Not yet implemented")
    }
}