package dev.chu.chulibrary.arch.data

import androidx.annotation.IntRange
import dev.chu.chulibrary.concurrent.AppDispatchers
import dev.chu.chulibrary.util.time.Time
import dev.chu.chulibrary.util.time.millis
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Repository 패턴을 위한 base class.
 * @param cachingDuration: in-memory cache 유지 시간
 * @param cachingCapacity: in-memory cache 보유 한도 (min:0 to max:30)
 * @see RemoteDataSource
 * @see LocalDataSource
 */
abstract class BaseRepository<Request, Response>(
    private val remoteDataSource: RemoteDataSource<Request, Response>? = null,
    private val localDataSource: LocalDataSource<Request, Response>? = null,
    private val cachingDuration: Time = 0.millis,
    @IntRange(from = 0, to = 30) protected val cachingCapacity: Int = 20
) {

    /**
     * [RemoteDataSource] 또는 [LocalDataSource] 로 부터 Data fetch.
     * - [localDataSource]의 사용 여부는 [LocalDataSource]를 구현하는 클래스에서 직접 판단해야 한다.
     *
     * @param request: 데이터 요청에 필요한 request 파라메터.
     * @param isForceRefresh: (false)in-memory 캐시 사용 가능시 캐싱 데이터를 반환한다. (true)[DataSource.fetchData]를 요청한다.
     * @param isForceRequestDataSource: (true)in-memory 캐시 사용 가능시 캐싱 데이터를 먼저 반환 후, [DataSource.fetchData]를 요청한다.
     * @param onSuccess: 데이터 fetch 성공시 요청되는 callback.
     */
    open suspend fun fetchData(
        request: Request,
        isForceRefresh: Boolean,
        isForceRequestDataSource: Boolean,
        onSuccess: suspend (Response) -> Unit
    ) = withContext(AppDispatchers.IO) {
        var isFromRemote = false
        val response = localDataSource?.fetchData(request, isForceRefresh)
            ?: remoteDataSource?.fetchData(request, isForceRefresh)?.also { isFromRemote = true }
            ?: throw Exception()

        val finalResponse = handleFetchSuccess(request, response, isForceRefresh, isForceRequestDataSource, onSuccess)
        try {
            if (isFromRemote) {
                localDataSource?.insertData(request, finalResponse)
            }
        } catch (e: Exception) {

        }
    }

    /**
     * 데이터 fetch 성공 후 수행 되는 base callback.
     * 추가적인 처리 작업 필요시 override 하여 사용한디.
     */
    protected open suspend fun handleFetchSuccess(
        request: Request,
        response: Response,
        isForceRefresh: Boolean,
        isForceRequestDataSource: Boolean,
        onSuccess: suspend (Response) -> Unit
    ): Response {
        onSuccess(response)
        return response
    }
}