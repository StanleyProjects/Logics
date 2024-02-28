package sp.kx.logics

/**
 * A utility class that provides [Logics].
 *
 * Usage:
 * ```
 * val provider = LogicsProvider()
 * val logics = provider<FooLogics>.get("foo")
 * assertTrue(logics === provider<FooLogics>.get("foo"))
 * provider<FooLogics>.remove("foo")
 * assertFalse(logics === provider<FooLogics>.get("foo"))
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.0
 */
class LogicsProvider(
    private val factory: LogicsFactory = SimpleLogicsFactory,
) {
    private data class CompositeKey<T : Logics>(val label: String, val type: Class<T>)

    private val logics = mutableMapOf<CompositeKey<out Logics>, Logics>()

    fun <T : Logics> get(label: String, type: Class<T>): T {
        return logics.getOrPut(CompositeKey(label = label, type = type)) { factory.create(type) } as T
    }

    fun <T : Logics> contains(label: String, type: Class<T>): Boolean {
        return logics.containsKey(CompositeKey(label = label, type = type))
    }

    fun <T : Logics> remove(label: String, type: Class<T>) {
        logics.remove(CompositeKey(label = label, type = type))?.clear()
    }
}

inline fun <reified T : Logics> LogicsProvider.get(label: String): T {
    return get(label = label, type = T::class.java)
}

inline fun <reified T : Logics> LogicsProvider.contains(label: String): Boolean {
    return contains(label = label, type = T::class.java)
}

inline fun <reified T : Logics> LogicsProvider.remove(label: String) {
    remove(label = label, type = T::class.java)
}
