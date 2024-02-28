package sp.kx.logics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SimpleLogicsFactoryTest {
    @Test
    fun createTest() {
        val factory: LogicsFactory = SimpleLogicsFactory
        val logics = factory.create(MockLogics::class.java)
        assertEquals(MockLogics::class.java, logics::class.java)
    }
}
