package dev.chu.chulibrary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.chu.chulibrary.arch.*
import dev.chu.chulibrary.test.TestDoubleBackend
import dev.chu.chulibrary.test.TestDoubleStringRepository
import dev.chu.chulibrary.test.TestViewModel01
import dev.chu.chulibrary.test.TestViewModel02
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun when_backend_fails_fallback_string_is_displayed_02() {
        val backend = TestDoubleBackend()
        backend.failWhenLoadingText = true

        val viewModel = TestViewModel02(backend)
        viewModel.loadText()

        val expectedText = TextResource.fromStringResId(R.string.app_name)
        Assert.assertEquals(expectedText, viewModel.textToDisplay.value)
        // data classes generate equals methods for us so we can compare them easily
    }

    @Test
    fun when_backend_fails_fallback_string_is_displayed_01() {
        val stringRepo = TestDoubleStringRepository()
        val backend = TestDoubleBackend()
        backend.failWhenLoadingText = false  // makes backend.getText() throw an exception

        val viewModel = TestViewModel01(backend, stringRepo)
        viewModel.loadText()

        Assert.assertEquals("some string", viewModel.textToDisplay.value)
    }
}