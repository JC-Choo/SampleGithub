package dev.chu.chulibrary.log

interface Loggable {
    /**
     * [Throwable] logging 처리
     */
    fun logException(t: Throwable)

    /**
     * [android.util.Log.VERBOSE] logging
     * [Logger]에서 Debug 모드인 경우 자동으로 [android.util.Log.v]를 호출
     */
    fun v(msg: String)

    /**
     * [android.util.Log.INFO] logging
     * [Logger]에서 Debug 모드인 경우 자동으로 [android.util.Log.i]를 호출
     */
    fun i(msg: String)

    /**
     * [android.util.Log.DEBUG] logging
     * [Logger]에서 Debug 모드인 경우 자동으로 [android.util.Log.d]를 호출
     */
    fun d(msg: String)

    /**
     * [android.util.Log.WARN] logging
     * [Logger]에서 Debug 모드인 경우 자동으로 [android.util.Log.w]를 호출
     */
    fun w(msg: String)

    /**
     * [android.util.Log.ERROR] logging
     * [Logger]에서 Debug 모드인 경우 자동으로 [android.util.Log.e]를 호출
     */
    fun e(msg: String)
}