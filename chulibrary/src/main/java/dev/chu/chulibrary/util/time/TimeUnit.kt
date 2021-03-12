package dev.chu.chulibrary.util.time

enum class TimeUnit {
    MILLISECOND {
        override val seconds: Double
            get() = 0.001
        override val javaTimeUnit: java.util.concurrent.TimeUnit
            get() = java.util.concurrent.TimeUnit.MILLISECONDS
    },
    SECOND {
        override val seconds: Double
            get() = 1.0
        override val javaTimeUnit: java.util.concurrent.TimeUnit
            get() = java.util.concurrent.TimeUnit.SECONDS
    },
    MINUTE {
        override val seconds: Double
            get() = 60.0
        override val javaTimeUnit: java.util.concurrent.TimeUnit
            get() = java.util.concurrent.TimeUnit.MINUTES
    },
    HOUR {
        override val seconds: Double
            get() = 3_600.0
        override val javaTimeUnit: java.util.concurrent.TimeUnit
            get() = java.util.concurrent.TimeUnit.HOURS
    },
    DAY {
        override val seconds: Double
            get() = 86_400.0
        override val javaTimeUnit: java.util.concurrent.TimeUnit
            get() = java.util.concurrent.TimeUnit.DAYS
    };

    abstract val seconds: Double
    abstract val javaTimeUnit: java.util.concurrent.TimeUnit
    fun conversionRate(timeUnit: TimeUnit): Double =
        seconds / timeUnit.seconds
}