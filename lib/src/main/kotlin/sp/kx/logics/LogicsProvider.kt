package sp.kx.logics

class LogicsProvider(
    private val factory: LogicsFactory = SimpleLogicsFactory,
) {
    private data class CompositeKey<T : Logics>(val label: String, val type: Class<T>)

    private val logics: MutableMap<CompositeKey<out Logics>, Logics> = mutableMapOf()

    fun <T : Logics> get(label: String, type: Class<T>): T {
        return logics.getOrPut(CompositeKey(label = label, type = type)) { factory.create(type) } as T
    }
}

inline fun <reified T : Logics> LogicsProvider.get(label: String): T {
    return get(label = label, type = T::class.java)
}
