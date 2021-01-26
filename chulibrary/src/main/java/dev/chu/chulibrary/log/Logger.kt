package dev.chu.chulibrary.log

import android.os.Build
import android.util.Log
import dev.chu.chulibrary.BuildConfig
import java.lang.IllegalArgumentException

/**
 * Debug 모드에서만 [android.util.Log] 출력이 가능한 Logger object.
 *
 * - Application 상에서 Log 출력 시 Custom 한 확장이 필요한 경우
 * - Crashlytics.logException 을 호출하거나, 자체 서버로 Log message 를 전송하고 싶을 때
 *
 * [Loggable] 을 구현하여 init 시점에 삽입하면 확장 구현이 함께 동작(로그 출력) 한다.
 */
object Logger : Loggable {

    private const val TAG_OLD_MAX_LENGTH: Int = 23
    private val loggers: MutableList<Loggable> = mutableListOf()
    private val isDebug: Boolean
        get() = BuildConfig.DEBUG

    fun init(vararg loggers: Loggable) {
        with(this.loggers) {
            if (isNotEmpty()) {
                safeThrow(IllegalArgumentException("Logger already initialized."))
                clear()
            }
            addAll(loggers)
        }
    }

    fun safeThrow(t: Throwable) {
        if (isDebug) throw t
        logException(t)
    }

    private fun log(priority: Int, msg: String) {
        if (!isDebug) return
        Log.println(priority, getCaller(), msg)
    }

    private fun getCaller(): String {
        val caller = Thread.currentThread().stackTrace[5]
        val tag = "${caller.className};${caller.lineNumber}"

        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) tag else tag.takeLast(TAG_OLD_MAX_LENGTH)
    }

    override fun logException(t: Throwable) {
        if (isDebug) t.printStackTrace()
        loggers.forEach { it.logException(t) }
    }

    override fun v(msg: String) {
        log(Log.VERBOSE, msg)
        loggers.forEach { it.v(msg) }
    }

    override fun i(msg: String) {
        log(Log.INFO, msg)
        loggers.forEach { it.i(msg) }
    }

    override fun d(msg: String) {
        log(Log.DEBUG, msg)
        loggers.forEach { it.d(msg) }
    }

    override fun w(msg: String) {
        log(Log.WARN, msg)
        loggers.forEach { it.w(msg) }
    }

    override fun e(msg: String) {
        log(Log.ERROR, msg)
        loggers.forEach { it.e(msg) }
    }
}