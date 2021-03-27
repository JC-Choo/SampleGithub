package dev.chu.githubsample.popular.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dev.chu.chulibrary.di.FragmentScope
import dev.chu.chulibrary.di.ViewModelInject
import dev.chu.chulibrary.di.ViewModelKey
import dev.chu.githubsample.main.MainFragment
import dev.chu.githubsample.main.MainViewModel
import dev.chu.githubsample.popular.PopularFragment
import dev.chu.githubsample.popular.PopularViewModel

@Module
abstract class PopularFragmentModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [PopularFragmentViewModelModule::class])
    abstract fun popularFragment(): PopularFragment
}

@Module
abstract class PopularFragmentViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PopularViewModel::class)
    abstract fun bindPopularFragmentViewModel(viewModel: PopularViewModel): ViewModel

    @Module
    companion object {
        @JvmStatic
        @ViewModelInject
        @Provides
        fun providePopularViewModel(
            fragment: PopularFragment,
            viewModelFactory: ViewModelProvider.Factory
        ): PopularViewModel = ViewModelProvider(fragment, viewModelFactory).get()
    }
}