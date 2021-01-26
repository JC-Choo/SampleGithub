package dev.chu.chulibrary

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.chu.chulibrary.test.TestDoubleBackend
import dev.chu.chulibrary.test.TestViewModel02
import dev.chu.chulibrary.arch.TextResource
import dev.chu.chulibrary.util.extensions.asString
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun when_backend_fails_fallback_string_is_displayed_02() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val resources = appContext.resources

        val backend = TestDoubleBackend()
        backend.failWhenLoadingText = true

        val viewModel = TestViewModel02(backend)
        viewModel.loadText()

        val expectedText = TextResource.fromStringResId(R.string.test_case).asString(resources)
        assertEquals(expectedText, viewModel.textToDisplay.value?.asString(resources))
        // data classes generate equals methods for us so we can compare them easily
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("dev.chu.chulibrary", appContext.packageName)
    }
}