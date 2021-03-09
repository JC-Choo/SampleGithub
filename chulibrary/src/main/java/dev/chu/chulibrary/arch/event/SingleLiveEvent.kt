package dev.chu.chulibrary.arch.event

import android.os.SystemClock
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import dev.chu.chulibrary.util.extensions.TAG
import java.util.concurrent.atomic.AtomicBoolean


/**
 * 네비게이션과 스낵바 메시지처럼 이벤트를 위해 사용되는, 구독 후 새 업데이트만 보내는 생명주기-인식 옵저버블
 *
 * 이건 이벤트와 관련된 일반적인 문제를 회피할 수 있다. : 구성 변경(회전과 같은)에서 옵저버가 활성 상태이면 업데이트를 내보낼 수 있다.
 * 이 LiveData 는 setValue() or call() 에 대한 명시적 호출이 있는 경우에만 옵저버블을 호출한다.
 *
 * "단 하나"의 옵저버에게만 변경이 통보된다.
 */
/**
 * ============================= SingleLiveEvent 사용 이유 ==========================
 * 즉, 일반적인 MutableLiveData 는 회전과 같은 변경 사항이 발생 시 새로 활성화 될때마다 같은 값을 observe 하고 있는 문제가 있다.
 * 이를 회피하기 위해 단일 이벤트를 보장하는, LiveData 를 확장한, SingleLiveEvent 를 사용한다.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    /**
     * AtomicType : 멀티스레딩과 같은 상황에서 동시에 한 자원에 접근하면 생길 수 있는 이슈에 명확하게 구분할 수 있는 도구
     */
    private val pending = AtomicBoolean(false)
    private val MIN_CLICK_INTERVAL: Long = 200
    private var lastClickTime: Long = 0

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner, { t ->
            /**
             * [compareAndSet] : expect 값과 비교해 같을 경우에만 update 값으로 변경.
             * 이렇게 되면 무조건 compare 가 진행되므로, 동시성 문제에서 벗어날 수 있다.
             */
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        // 참고 : https://www.charlezz.com/?p=44609
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime: Long = currentClickTime - lastClickTime
        lastClickTime = currentClickTime

        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            return
        }
        pending.set(true)
        super.setValue(t)
    }
    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}