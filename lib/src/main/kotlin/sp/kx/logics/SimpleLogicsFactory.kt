package sp.kx.logics

/**
 * An implementation of the [LogicsFactory] interface
 * that creates [Logics] objects using an empty constructor of the appropriate type.
 *
 * Used by default in [LogicsProvider].
 *
 * Usage:
 * ```
 * val factory = SimpleLogicsFactory
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
object SimpleLogicsFactory : LogicsFactory {
    override fun <T : Logics> create(type: Class<T>): T {
        return type
            .getConstructor()
            .newInstance()
    }
}
