package dev.chu.githubsample

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.chu.githubsample.di.DaggerAppComponent

class MainApplication: DaggerApplication() {

    companion object {
        /**
         * [Volatile]
         * - 변수를 MainMemory 에 저장하겠다는 것을 명시하는 annotation
         * - 매번 값을 read 할 때마다 CPU cache 가 아닌 MainMemory 에서 읽는다.
         * - 매번 값을 write 할 때마다 MainMemory 에까지 작성한다.
         *
         * -> 멀티 쓰레드 환경에서 적합하며, 한 쓰레드에서만 작성하고 나머진 읽는 환경에서 적합하다.
         * 하지만 그 둘 이상의 쓰레드에서 동시에 쓸 경운 문제가 생길 수 있다. 이럴 경우엔 synchronize 가 더 적합하다.
         * 참고 : https://nesoy.github.io/articles/2018-06/Java-volatile
         */
        @Volatile
        private var instance: MainApplication? = null

        /**
         * [JvmStatic]
         * - 설정 시 static 변수의 get/set 함수를 자동으로 만들어준다.
         * - static 변수를 자동으로 만들기에, Java 에서 호출 시 MainApplication.Companion.get() 이 아닌 MainApplication.get() 호출 가능
         */
        @JvmStatic
        fun get(): MainApplication {
            if (instance == null) {
                instance = MainApplication()
            }
            return instance!!
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}