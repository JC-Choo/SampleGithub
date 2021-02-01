package dev.chu.chulibrary.arch.event

import androidx.lifecycle.Observer

/**
 * Event Handler 구분을 위한 Key interface
 * - Activity or Fragment 에서 구현
 */
interface EventHandler {
    /**
     * Event Handler 를 구분하는 key
     * @see Event.getContentIfNotHandled
     */
    val eventHandleKey: String
}

/**
 * [androidx.lifecycle.LiveData]를 통해 observe 할 Event 전송용 wrapper class.
 * - 기 처리된 [content]는 사용할 수 없다.
 * - 에러 메시지와 같은 [message]를 함꼐 사용할 수 있다.
 * - [EventObserver]와 함꼐 사용하도록 한다.
 */
open class Event<T>(
    private val content: T,
    val message: String? = null
) {
    private val handledKey: MutableSet<String> = HashSet()

    fun getContentIfNotHandled(owner: EventHandler): T? {
        val hashCode = owner.eventHandleKey

        return if (handledKey.contains(hashCode)) {
            null
        } else {
            handledKey.add(hashCode)
            content
        }
    }

    /**
     * 이미 처리된 것을 포함한 [content] 값을 반환한다.
     */
    fun peekContent(): T = content
}

/**
 * [Event]를 observe 할 때 사용할 [Observer] class.
 * - [owner] : 해당 [Event]를 실제 처리하는 객체
 */
class EventObserver<T>(
    private val owner: EventHandler,
    private val onEventInHandledContent: (T, String?) -> Unit
): Observer<Event<T>> {

    override fun onChanged(t: Event<T>?) {
        t?.getContentIfNotHandled(owner)?.let {
            onEventInHandledContent(it, t.message)
        }
    }
}