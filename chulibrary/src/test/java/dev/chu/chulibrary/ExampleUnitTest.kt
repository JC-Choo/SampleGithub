package dev.chu.chulibrary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.chu.chulibrary.arch.TestDoubleBackend
import dev.chu.chulibrary.arch.TestDoubleStringRepository
import dev.chu.chulibrary.arch.TestViewModel
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
    fun when_backend_fails_fallback_string_is_displayed() {
        val stringRepo = TestDoubleStringRepository()
        val backend = TestDoubleBackend()
        backend.failWhenLoadingText = false  // makes backend.getText() throw an exception

        val viewModel = TestViewModel(backend, stringRepo)
        viewModel.loadText()

        Assert.assertEquals("some string", viewModel.textToDisplay.value)
    }
}