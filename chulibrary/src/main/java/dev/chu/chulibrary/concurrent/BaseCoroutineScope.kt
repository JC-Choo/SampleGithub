package dev.chu.chulibrary.concurrent

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * [CoroutineScope]을 상속받아 사용할 경우
 * [CoroutineScope]의 동작을 제어하기 위해 [Job]을 이용한다.
 *
 * 참고 : https://thdev.tech/kotlin/2019/04/05/Init-Coroutines/
 */
interface BaseCoroutineScope: CoroutineScope {
    /**
     * 백그라운드 작업.
     * 생명주기가 끝날 시 함께 cancel 해주어야 한다.
     */
    val job: Job

    /**
     * Coroutine job cancel
     */
    fun releaseCoroutine()
}