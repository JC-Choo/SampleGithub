package dev.chu.chulibrary.concurrent

import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * [BaseCoroutineScope]을 상속받아 UI 에서 작업할 [kotlinx.coroutines.CoroutineScope]을 정의한다.
 * UI 용 [CoroutineContext]인 [AppDispatchers.UI]에서 실행하도록 한다.
 */
class UICoroutineScope(
    private val dispatchers: CoroutineContext = AppDispatchers.UI
): BaseCoroutineScope {

    override val job: Job
        get() = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatchers + job

    override fun releaseCoroutine() {
        job.cancel()
    }
}