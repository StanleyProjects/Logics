package sp.kx.logics

import java.io.Closeable
import java.util.concurrent.atomic.AtomicBoolean

internal class MockCloseable : Closeable {
    private val closed = AtomicBoolean(false)

    fun isClosed(): Boolean {
        return closed.get()
    }

    override fun close() {
        closed.set(true)
    }
}
