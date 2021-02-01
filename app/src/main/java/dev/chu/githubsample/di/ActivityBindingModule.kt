package dev.chu.githubsample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.chu.chulibrary.di.ActivityScope
import dev.chu.githubsample.MainActivity
import dev.chu.githubsample.main.di.MainModule

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainModule::class
        ]
    )
    abstract fun mainActivity(): MainActivity
}