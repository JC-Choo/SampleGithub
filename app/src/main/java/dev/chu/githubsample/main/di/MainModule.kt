package dev.chu.githubsample.main.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.chu.chulibrary.di.FragmentScope
import dev.chu.githubsample.main.MainFragment

@Module
abstract class MainModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [MainFragmentViewModelModule::class])
    abstract fun mainFragment(): MainFragment
}