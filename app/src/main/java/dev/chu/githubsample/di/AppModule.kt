package dev.chu.githubsample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.chu.chulibrary.core.AppPref
import dev.chu.chulibrary.di.ForApplication
import dev.chu.githubsample.MainApplication
import javax.inject.Singleton

@Module
class AppModule {
    @ForApplication
    @Provides
    fun provideContext(app: MainApplication): Context = app

    @Singleton
    @Provides
    fun provideAppPref(app: MainApplication): AppPref = app.appPref
}