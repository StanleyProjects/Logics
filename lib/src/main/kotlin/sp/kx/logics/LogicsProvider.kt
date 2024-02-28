package sp.kx.logics

/**
 * A utility class that provides [Logics].
 *
 * Usage:
 * ```
 * val provider = LogicsProvider()
 * val logics = provider<FooLogics>.get("foo")
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.0
 */
class LogicsProvider(
    private val factory: LogicsFactory = SimpleLogicsFactory,
) {
    private data class CompositeKey<T : Logics>(val label: String, val type: Class<T>)

    private val logics = mutableMapOf<CompositeKey<out Logics>, Logics>()

    /**
     * Returns an existing [Logics] or creates a new one, associated with this [LogicsProvider].
     *
     * Usage:
     * ```
     * val provider = LogicsProvider()
     * val logics = provider.get("foo", FooLogics::class.java)
     * assertTrue(logics === provider.get("foo", FooLogics::class.java))
     * ```
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.1.0
     */
    fun <T : Logics> get(label: String, type: Class<T>): T {
        return logics.getOrPut(CompositeKey(label = label, type = type)) { factory.create(type) } as T
    }

    /**
     * The method tells whether [Logics] already exists based on the given parameters.
     *
     * Usage:
     * ```
     * val provider = LogicsProvider()
     * assertFalse(provider.contains("foo", FooLogics::class.java))
     * val logics = provider<FooLogics>.get("foo")
     * assertTrue(provider.contains("foo", FooLogics::class.java))
     * ```
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.1.0
     */
    fun <T : Logics> contains(label: String, type: Class<T>): Boolean {
        return logics.containsKey(CompositeKey(label = label, type = type))
    }

    /**
     * Remove an existing [Logics] from this [LogicsProvider] memory and clear it.
     *
     * Usage:
     * ```
     * val provider = LogicsProvider()
     * val logics = provider<FooLogics>.get("foo")
     * assertTrue(logics === provider<FooLogics>.get("foo"))
     * provider.remove("foo", FooLogics::class.java)
     * assertFalse(logics === provider<FooLogics>.get("foo"))
     * ```
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.1.0
     */
    fun <T : Logics> remove(label: String, type: Class<T>) {
        logics.remove(CompositeKey(label = label, type = type))?.clear()
    }
}

/**
 * Sugar for the [LogicsProvider.get] method.
 *
 * Usage:
 * ```
 * val provider = LogicsProvider()
 * val logics = provider.get<FooLogics>("foo")
 * assertTrue(logics === provider.get<FooLogics>("foo"))
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.0
 */
inline fun <reified T : Logics> LogicsProvider.get(label: String): T {
    return get(label = label, type = T::class.java)
}

/**
 * Sugar for the [LogicsProvider.contains] method.
 *
 * Usage:
 * ```
 * val provider = LogicsProvider()
 * assertFalse(provider.contains<FooLogics>("foo"))
 * val logics = provider<FooLogics>.get("foo")
 * assertTrue(provider.contains<FooLogics>("foo"))
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.0
 */
inline fun <reified T : Logics> LogicsProvider.contains(label: String): Boolean {
    return contains(label = label, type = T::class.java)
}

/**
 * Sugar for the [LogicsProvider.remove] method.
 *
 * Usage:
 * ```
 * val provider = LogicsProvider()
 * val logics = provider<FooLogics>.get("foo")
 * assertTrue(logics === provider<FooLogics>.get("foo"))
 * provider.remove<FooLogics>("foo")
 * assertFalse(logics === provider<FooLogics>.get("foo"))
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.0
 */
inline fun <reified T : Logics> LogicsProvider.remove(label: String) {
    remove(label = label, type = T::class.java)
}
