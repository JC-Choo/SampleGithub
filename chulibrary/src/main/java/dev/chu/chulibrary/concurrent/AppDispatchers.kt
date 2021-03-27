package dev.chu.chulibrary.concurrent

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Application 전역에서 사용할 [CoroutineDispatcher].
 */
object AppDispatchers {

    /**
     * UI 관련 작업을 할 때 사용한다.
     */
    val UI: CoroutineDispatcher = Dispatchers.Main

    /**
     * 비교적 짧은 background 작업을 할 때 사용한다.
     */
    val Default: CoroutineDispatcher = Dispatchers.Default

    /**
     * background data fetch (파일 읽기, API 등)의 작업이 필요할 때 사용한다.
     */
    val IO: CoroutineDispatcher = Dispatchers.IO
}