package dev.chu.githubsample

import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class ExampleUnitTest {
    private val locked00 = AtomicBoolean(false)
    private val locked01 = AtomicInteger(100)

    @Test
    fun atomicTest() {
        val a = locked00.getAndSet(true)
        val b = locked01.getAndSet(99)
        println("a = $a, b = $b")
        println("00 = ${locked00.get()}")
        println("00 = ${lock00()}")
        println("00 = ${locked00.get()}")
        println("01 = ${locked01.get()}")
        println("01 = ${lock01()}")
        println("01 = ${locked01.get()}")
    }

    private fun lock00(): Boolean {
        return locked00.compareAndSet(false, true)
    }

    private fun lock01(): Boolean {
        return locked01.compareAndSet(100, 1000)
    }
}