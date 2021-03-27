package dev.chu.chulibrary.util.time

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

data class Time(
    val value: Double,
    val unit: TimeUnit
): Comparable<Time> {

    companion object {
        @JvmStatic
        private fun Number.toSafeDouble() =
            if (this is Float) {
                toBigDecimal().toDouble()
            } else {
                toDouble()
            }

        @SuppressLint("SimpleDateFormat")
        @JvmStatic
        fun valueOf(time: String, format: String, unit: TimeUnit): Time {
            val dateFormat = SimpleDateFormat(format)
            val date = dateFormat.parse(time) ?: error("Unable to parse $time.")

            return Time(date.time, unit)
        }
    }

    constructor(value: Number, unit: TimeUnit): this(value.toSafeDouble(), unit)

    val inMillis: Double
        get() = toMillis.value

    val inSeconds: Double
        get() = toSeconds.value

    val inMinutes: Double
        get() = toMinutes.value

    val inHours: Double
        get() = toHours.value

    val inDays: Double
        get() = toDays.value

    val toMillis: Time
        get() = convertTo(TimeUnit.MILLISECOND)

    val toSeconds: Time
        get() = convertTo(TimeUnit.SECOND)

    val toMinutes: Time
        get() = convertTo(TimeUnit.MINUTE)

    val toHours: Time
        get() = convertTo(TimeUnit.HOUR)

    val toDays: Time
        get() = convertTo(TimeUnit.DAY)
    
    private fun convertTo(timeUnit: TimeUnit): Time = Time(value * unit.conversionRate(timeUnit), timeUnit)

    operator fun plus(other: Time): Time = Time(value + other.convertTo(unit).value, unit)

    operator fun minus(other: Time): Time = Time(value - other.convertTo(unit).value, unit)

    operator fun plus(other: Number): Time = Time(value + other.toSafeDouble(), unit)

    operator fun minus(other: Number): Time = Time(value - other.toSafeDouble(), unit)

    operator fun times(other: Number): Time = Time(value * other.toSafeDouble(), unit)

    operator fun div(other: Number): Time = Time(value / other.toSafeDouble(), unit)

    operator fun inc(): Time = Time(value + 1, unit)

    operator fun dec(): Time = Time(value - 1, unit)
    
    override operator fun compareTo(other: Time): Int {
        return value.compareTo(other.convertTo(unit).value)
    }

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is Time -> false
        value == other.value && unit == other.unit -> true
        else -> value == other.convertTo(unit).value
    }

    override fun hashCode(): Int = inSeconds.hashCode()

    override fun toString(): String = "$value ${unit.toString().toLowerCase(Locale.getDefault())}"
}

val currentTime: Time
    get() = System.currentTimeMillis().millis

val Number.millis: Time
    get() = Time(this, TimeUnit.MILLISECOND)

val Number.seconds: Time
    get() = Time(this, TimeUnit.SECOND)

val Number.minutes: Time
    get() = Time(this, TimeUnit.MINUTE)

val Number.hours: Time
    get() = Time(this, TimeUnit.HOUR)

val Number.days: Time
    get() = Time(this, TimeUnit.DAY)

infix fun ClosedRange<Time>.step(step: Time): Iterable<Time> {
    require(start.value.isFinite())
    require(endInclusive.value.isFinite())

    val sequence = generateSequence(start) { previous ->
        if (previous.value == Double.POSITIVE_INFINITY) {
            return@generateSequence null
        }
        val next = previous + step
        if (next > endInclusive) {
            null
        } else {
            next
        }
    }

    return sequence.asIterable()
}