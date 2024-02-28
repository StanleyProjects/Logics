package sp.kx.logics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicBoolean

internal class LogicsProviderTest {
    @Test
    fun getTest() {
        val provider = LogicsProvider()
        val label = "foo"
        val type = MockLogics::class.java
        val logics = provider.get(label, type)
        assertEquals(type, logics::class.java)
        assertTrue(logics === provider.get(label, type))
    }

    @Test
    fun getExTest() {
        val provider = LogicsProvider()
        val label = "foo"
        val logics = provider.get<MockLogics>(label)
        assertEquals(MockLogics::class.java, logics::class.java)
        assertTrue(logics === provider.get<MockLogics>(label))
    }

    @Test
    fun containsTest() {
        val provider = LogicsProvider()
        val label = "foo"
        assertFalse(provider.contains<MockLogics>(label))
        provider.get<MockLogics>(label)
        assertTrue(provider.contains<MockLogics>(label))
    }

    @Test
    fun removeTest() {
        val closable = MockCloseable()
        class TestLogics : Logics(mapOf("foo" to closable))
        val provider = LogicsProvider(MockLogicsFactory(TestLogics()))
        val label = "foo"
        assertFalse(provider.contains<TestLogics>(label))
        val logics = provider.get<TestLogics>(label)
        assertTrue(provider.contains<TestLogics>(label))
        assertFalse(closable.isClosed())
        assertTrue(logics.isActive())
        provider.remove<TestLogics>(label)
        assertFalse(provider.contains<TestLogics>(label))
        assertTrue(closable.isClosed())
        assertFalse(logics.isActive())
    }
}
