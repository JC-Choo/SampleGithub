package dev.chu.chulibrary.arch.event

/**
 * Data Fetch 상태 표현용 enum class.
 */
enum class FetchStatus {
    LOADING,
    SUCCESS,
    FAILED
}

/**
 * [FetchStatus]의 wrapper class.
 * - [androidx.lifecycle.LiveData]를 이용해 Event<FetchStatus> 를 observe 하여, 각 [FetchStatus]에 부합하는 처리를 할 수있도록 돕는다.
 *
 * - [FetchStatus.LOADING] -> [LOADING]
 * - [FetchStatus.SUCCESS] -> [SUCCESS]
 * - [FetchStatus.FAILED] -> [failed]를 이용해 에러 [message]를 함께 전달한다.
 */
//public class FetchEvent private constructor(
//    status: FetchStatus,
//    message: String? = null
//) : Event<FetchStatus>(status, message) {
//
//    public companion object {
//        public val SUCCESS: FetchEvent
//            get() = FetchEvent(FetchStatus.SUCCESS)
//
//        public val LOADING: FetchEvent
//            get() = FetchEvent(FetchStatus.LOADING)
//
//        public fun failed(message: String? = null): FetchEvent = FetchEvent(FetchStatus.FAILED, message)
//    }
//}