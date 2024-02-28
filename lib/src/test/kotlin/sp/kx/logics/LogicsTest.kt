package sp.kx.logics

import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.Closeable
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

internal class LogicsTest {
    @Test
    fun getCoroutineScopeTest() {
        class LogicsForTest : Logics() {
            fun test() {
                val key = "foo"
                val scope = getCoroutineScope(key) { CloseableCoroutineScope(UnconfinedTestDispatcher()) }
                assertTrue(scope === getCoroutineScope(key) { error("Impossible!") })
                assertTrue(scope !== getCoroutineScope("bar") { CloseableCoroutineScope(UnconfinedTestDispatcher()) })
            }
        }
        val logics = LogicsForTest()
        logics.test()
    }

    @Test
    fun coroutineScopeTest() {
        class LogicsForTest : Logics() {
            fun test() {
                val scope = coroutineScope
                assertTrue(scope === coroutineScope)
            }
        }
        val logics = LogicsForTest()
        logics.test()
    }

    @Test
    fun clearTest() {
        val foo = object : Closeable {
            private val closed = AtomicBoolean(false)

            fun isClosed(): Boolean {
                return closed.get()
            }

            override fun close() {
                closed.set(true)
            }
        }
        class LogicsForTest : Logics(tags = mapOf("foo" to foo)) {
            fun assert(expected: Boolean) {
                assertEquals(expected, foo.isClosed())
            }
        }
        val logics = LogicsForTest()
        logics.assert(false)
        logics.clear()
        logics.assert(true)
    }

    @Test
    fun coroutineContextTest() {
        runTest(timeout = 2.seconds) {
            val mockKey = MockKey()
            val mockElement = MockElement(mockKey)
            class LogicsForTest : Logics(MockCoroutineContext(elements = mapOf(mockKey to mockElement))) {
                fun test() {
                    val checked = AtomicBoolean(false)
                    launch {
                        val element = coroutineContext[mockKey]
                        checkNotNull(element)
                        assertTrue(element === mockElement)
                        checked.set(true)
                    }
                    val start = System.currentTimeMillis().milliseconds
                    while (!checked.get()) {
                        val now = System.currentTimeMillis().milliseconds
                        if (now - start > 1.seconds) error("Timeout!")
                    }
                }
            }
            val logics = LogicsForTest()
            logics.test()
        }
    }

    @Test
    fun clearCoroutineContextTest() {
        runTest(timeout = 2.seconds) {
            class LogicsForTest : Logics() {
                fun before() {
                    val checked = AtomicBoolean(false)
                    launch {
                        checked.set(true)
                    }
                    val start = System.currentTimeMillis().milliseconds
                    while (!checked.get()) {
                        val now = System.currentTimeMillis().milliseconds
                        if (now - start > 1.seconds) error("Timeout!")
                    }
                }
                fun after() {
                    val checked = AtomicBoolean(false)
                    assertFalse(checked.get())
                    assertThrows(IllegalStateException::class.java) {
                        launch {
                            checked.set(true)
                        }
                    }
                    val start = System.currentTimeMillis().milliseconds
                    while (true) {
                        assertFalse(checked.get())
                        val now = System.currentTimeMillis().milliseconds
                        if (now - start > 1.seconds) break
                    }
                }
            }
            val logics = LogicsForTest()
            logics.before()
            logics.clear()
            logics.after()
        }
    }

    @Test
    fun clearErrorTest() {
        class LogicsForTest : Logics()
        val logics = LogicsForTest()
        logics.clear()
        assertThrows(IllegalStateException::class.java) {
            logics.clear()
        }
    }
}
