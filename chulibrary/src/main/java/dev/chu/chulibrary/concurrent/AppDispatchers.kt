package dev.chu.chulibrary.concurrent

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Application 전역에서 사용할 [CoroutineDispatcher].
 */
object AppDispatchers {
    /**
     * UI Thread 를 사용해 UI 관련 작업을 할 때 사용
     */
    val UI: CoroutineDispatcher = Dispatchers.Main

    /**
     * CPU 사용량이 많은 작업에 사용하며, 주 쓰레드에서 작업하기에는 긴 작업들에 알맞음
     */
    val Default: CoroutineDispatcher = Dispatchers.Default

    /**
     * 네트워크, 디스크 사용 시 사용(파일을 읽고 쓰고, 소켓을 읽고 쓰고, 작업을 멈추는 등에 최적화)
     */
    val IO: CoroutineDispatcher = Dispatchers.IO
}