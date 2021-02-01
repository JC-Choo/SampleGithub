package dev.chu.githubsample.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dev.chu.chulibrary.di.ViewModelInject
import dev.chu.chulibrary.di.ViewModelKey
import dev.chu.githubsample.main.MainFragment
import dev.chu.githubsample.main.MainViewModel

@Module
abstract class MainFragmentViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Module
    companion object {
        @JvmStatic
        @ViewModelInject
        @Provides
        fun provideMainViewModel(
            fragment: MainFragment,
            factory: ViewModelProvider.Factory
        ): MainViewModel =
            ViewModelProvider(fragment, factory).get()
    }
}