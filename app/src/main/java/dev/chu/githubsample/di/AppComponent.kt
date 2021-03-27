package dev.chu.githubsample.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.chu.chulibrary.di.ViewModelModule
import dev.chu.githubsample.MainApplication
import javax.inject.Singleton

/**
 * Dagger 를 사용하기 위한 컴포넌트(컨테이너, 오브젝트 그래프) 정의
 * - 바인딩된 모듈로부터 오브젝트 그래프를 생성하는 핵심적인 역할
 * - [AndroidSupportInjectionModule] : dagger.android 사용을 위한 설정
 * - [AppModule] : 애플리케이션 스코프 모듈
 * - [ActivityModule] : 액티비티 스코프 모듈
 * - [ViewModelModule] : ViewModelProvider.Factory 의 의존성을 제공하는 모듈
 * - 안드로이드 애플리케이션 컴포넌트 팩토리 정의 : [Component.Factory]
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ViewModelModule::class
    ]
)
abstract class AppComponent : AndroidInjector<MainApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<MainApplication>
}