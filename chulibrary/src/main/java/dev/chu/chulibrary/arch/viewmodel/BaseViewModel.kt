package dev.chu.chulibrary.arch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.annotation.CallSuper
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import dev.chu.chulibrary.arch.data.BaseRepository
import dev.chu.chulibrary.arch.list.ListItem
import dev.chu.chulibrary.concurrent.AppDispatchers
import dev.chu.chulibrary.log.Logger
import dev.chu.chulibrary.util.time.Time
import dev.chu.chulibrary.util.time.currentTime
import dev.chu.chulibrary.util.time.millis
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<Request, Response>(
    private val repository: BaseRepository<Request, Response>,
    private val refreshDuration: Time = 0.millis,
    private val alwaysRequestDataSource: Boolean = false,
    private val fetchFailedMessage: String = "일시적인 오류로 연결할 수 없습니다.\n잠시 후 다시 시도해주세요.",
    private val networkFailedMessage: String = "네트워크에 접속할 수 없습니다.\n네트워크 연결 상태를 확인해주세요.",
    private val pagingFailedMessage: String = "일시적인 오류로 다음 페이지를 불러올 수 없습니다.\n잠시 후 다시 시도해주세요."
): ViewModel(), CoroutineScope {

    /**
     * 현재 [BaseViewModel] 상의 전체 coroutine 의 root [Job]
     */
    private val job: Job = SupervisorJob()

    /**
     * [BaseViewModel]의 [CoroutineContext]
     * [AppDispatchers.UI] 상에서 동작
     */
    override val coroutineContext: CoroutineContext
        get() = AppDispatchers.UI + job

    /**
     * 마지막 [fetchData]에 사용된 [Request]
     */
    var lastRequest: Request? = null

    /**
     * 마지막 [fetchData]의 성공 시간.
     */
    var lastSuccessTime = 0.millis
        protected set

    /**
     * 자동 새로고침 사용 여부.
     * [refreshDuration]값으로 판단한다.
     */
    val isAutoRefreshEnabled
        get() = refreshDuration > 0.millis

    /**
     * API [Response] entity 를 담는 [LiveData].
     *
     * - Repository 결과가 바뀔 때마다 자동으로 업데이트 된다.
     * - 이 값을 observe 하여 [createList]로 UI 상에 노출되는 리스트를 생성하게 된다.
     * - [BaseAdapter]에 전달한다.
     *
     * @see items
     */
    val data = MutableLiveData<Response>()

    val items: LiveData<List<ListItem<*>>> = data.switchMap {
        liveData(coroutineContext + AppDispatchers.Default) {
            emit(createList(it))
        }
    }

    /**
     * data fetch 진행 상태 알림용 [FetchEvent].
     *
     * - [BaseFragment]와 같은 View 에서 이 값을 observe 하며 loading 관련 view 를 업데이트 한다.
     *
     * @see ArkFragment.observeViewModel
     */
    public open val fetchEvent: MutableLiveData<FetchEvent> = MutableLiveData()

    /**
     * 완료되지 않은 fetch 작업이 있으면 [onCleared] 시점에 모두 취소한다.
     */
    @CallSuper
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    /**
     * [repository]에 새로고침을 요청한.
     *
     * @param force false 이면 in-memory cached response 를 사용할 수 있다.
     */
    open fun refresh(force: Boolean = false) {
        if (force || (isAutoRefreshEnabled && lastSuccessTime + refreshDuration < currentTime)) {
            lastRequest?.let { fetchData(it, true) }
        }
    }

    /**
     * [request]에 해당하는 data fetch 작업을 [repository]에 요청한다.
     *
     * - [Response]가 오기 전 다른 [fetchData] 요청이 있을 경우,
     * - 이전 요청은 취소하지는 않으며, 이전 요청에 대한 UI 업데이트 발생하지 않는다.
     *
     * @see BaseRepository.fetchData
     * @see BaseViewModel.onFetchDataSuccess
     */
    public open fun fetchData(request: Request, force: Boolean = false): Job = launch {
//        pagingJobs.forEach {
//            it.key.value = PagingEvent.RESET
//            it.value.cancel()
//        }

        lastRequest = request

        val fetchEvent = fetchEvent
        fetchEvent.value = FetchEvent.LOADING

        try {
            repository.fetchData(request, force, alwaysRequestDataSource) {
                onFetchDataSuccess(true, request, it, fetchEvent)
            }
        } catch (e: Exception) {
            onFetchException(e, fetchEvent)
        }
    }

    /**
     * [fetchData] 성공 시 호출되는 callback.
     */
    protected open suspend fun onFetchDataSuccess(
        bindResponse: Boolean = true,
        request: Request,
        response: Response,
        fetchEvent: MutableLiveData<FetchEvent>
    ) = withContext(AppDispatchers.UI) {
        if (request != lastRequest) return@withContext
        if (bindResponse) data.value = response
        lastSuccessTime = currentTime
        fetchEvent.value = FetchEvent.SUCCESS
    }

    /**
     * [BaseRepository.fetchData] 에러 핸들링.
     */
    protected open suspend fun onFetchException(
        e: Exception,
        fetchEvent: MutableLiveData<FetchEvent>
    ) = withContext(AppDispatchers.UI) {
        val message = when (e) {
            is CancellationException -> return@withContext
            is HttpException, is KotlinNullPointerException -> e.message ?: fetchFailedMessage
            else -> {
                if (e !is IOException) {
                    Logger.safeThrow(e)
                }
                networkFailedMessage
            }
        }
        fetchEvent.value = FetchEvent.failed(message)
    }

    /**
     * API [Response] [data]를 [BaseAdapter]에서 사용할 리스트 형태로 변경한다.
     * 변경된 리스트 안에 들어있는 [ListItem]구현체가 [dev.chu.chulibrary.arch.list.BaseViewHolder]에 바인딩 된다.
     *
     * @see BaseAdapter.list
     */
    @WorkerThread
    protected abstract suspend fun createList(data: Response?): List<ListItem<*>>
}