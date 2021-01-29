package dev.chu.githubsample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.chu.chulibrary.di.ActivityScope
import dev.chu.githubsample.MainActivity

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [

        ]
    )
    abstract fun mainActivity(): MainActivity
}