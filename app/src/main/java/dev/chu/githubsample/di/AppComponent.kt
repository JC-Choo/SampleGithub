package dev.chu.githubsample.di

import dagger.Component
import dagger.android.AndroidInjector
import dev.chu.githubsample.MainApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [

    ]
)
abstract class AppComponent : AndroidInjector<MainApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<MainApplication>
}