package dev.chu.githubsample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.chu.chulibrary.di.ApplicationContext
import dev.chu.githubsample.MainApplication

/**
 * [Module]
 * - 컴포넌트에 의존성을 제공하는 역할
 * - 객체의 의존성을 주입하기 위해서 사용되는 개념 (Module 과 Provider Methods)
 *
 * [Provides]
 * - 컴파일 타임에 의존성을 제공하는 바인드된 프로바이더를 생성하기 위핸 애노테이션
 * - 같은 타입의 반환형을 가진 함수가 있으면 안된다. -> Named or Qualifier
 */
@Module
class AppModule {
    @ApplicationContext
    @Provides
    fun provideContext(app: MainApplication): Context = app
}