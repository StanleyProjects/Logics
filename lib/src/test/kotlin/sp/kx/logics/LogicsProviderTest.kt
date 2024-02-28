package sp.kx.logics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

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
}
