package dev.chu.chulibrary.util.extensions

val Any.TAG: String
    get() = this::class.java.simpleName ?: this.toString()