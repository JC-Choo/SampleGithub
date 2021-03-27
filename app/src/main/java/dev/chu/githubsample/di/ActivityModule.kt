package dev.chu.githubsample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.chu.chulibrary.di.ActivityScope
import dev.chu.githubsample.MainActivity
import dev.chu.githubsample.main.di.MainFragmentModule
import dev.chu.githubsample.popular.di.PopularFragmentModule

/**
 * MainActivity 를 위한 서브 컴포넌트를 정의
 * - [ActivityScope] : Activity 용 scope
 */
@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainFragmentModule::class,
            PopularFragmentModule::class
        ]
    )
    abstract fun mainActivity(): MainActivity
}