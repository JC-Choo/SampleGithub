package dev.chu.chulibrary.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    /**
     * Binds 어노테이션은 Provides 와 같은 기능을 수행
     * Provides 로 선언한 코드의 양보다 훨씬 적고 효율적이다.
     * 매개변수로 반환 타입으로 할당 가능한 단 한 개의 변수만 허용하기에, Provides 보다 제한적이다.
     *
     * 모듈 내의 추상 메서드에 붙이며, 반드시 하나의 매개 변수만을 가져야 한다.
     * 매개 변수를 반환형으로 바인드할 수 있다.
     */
    @Binds
    abstract fun provideViewModelFactory(factory: BaseViewModelFactory): ViewModelProvider.Factory
}