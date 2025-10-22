package sp.kx.logics

/**
 * Implementations of `Factory` interface are responsible to instantiate [Logics].
 *
 * Usage:
 * ```
 * val factory = object : LogicsFactory {
 *     override fun <T : Logics> create(type: Class<T>): T {
 *         return type
 *             .getConstructor()
 *             .newInstance()
 *     }
 * }
 * val provider = LogicsProvider(factory = factory)
 * val logics = provider<FooLogics>.get("foo")
 * coroutineScope.launch {
 *     logics.state.collect {
 *         onState(it)
 *     }
 * }
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.1.0
 */
interface LogicsFactory {
    /**
     * Creates a new instance of the given [type].
     *
     * Usage:
     * ```
     * val factory = object : LogicsFactory {
     *     override fun <T : Logics> create(type: Class<T>): T {
     *         return type
     *             .getConstructor()
     *             .newInstance()
     *     }
     * }
     * ```
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.1.0
     */
    fun <T : Logics> create(type: Class<T>): T
}
