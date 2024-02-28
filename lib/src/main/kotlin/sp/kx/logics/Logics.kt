package sp.kx.logics

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.Closeable
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * It is a class that is responsible for preparing and managing the data for UI.
 * It also handles the communication of the UI with the rest of the application (e.g. calling the business logic classes).
 * It is always created in association with a scope and will be retained as long as the scope is alive.
 *
 * Usage:
 * ```
 * val provider = LogicsProvider()
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
open class Logics {
    private val coroutineContext: CoroutineContext
    private val tags: MutableMap<String, Closeable>
    private var isCleared = false
    protected val coroutineScope: CoroutineScope
        get() {
            return getCoroutineScope(
                key = "${this::class.java.name}:COROUTINE_SCOPE",
                supplier = { CloseableCoroutineScope(SupervisorJob() + coroutineContext) },
            )
        }

    constructor(
        coroutineContext: CoroutineContext,
        tags: Map<String, Closeable>,
    ) {
        this.coroutineContext = coroutineContext
        this.tags = tags.toMutableMap()
    }

    constructor(coroutineContext: CoroutineContext) : this(
        coroutineContext = coroutineContext,
        tags = emptyMap(),
    )

    constructor(tags: Map<String, Closeable>) : this(
        coroutineContext = EmptyCoroutineContext,
        tags = tags,
    )

    constructor() : this(
        coroutineContext = EmptyCoroutineContext,
        tags = emptyMap(),
    )

    internal fun isActive(): Boolean {
        return !isCleared
    }

    protected fun <T> getCoroutineScope(
        key: String,
        supplier: () -> T,
    ): CoroutineScope where T : Closeable, T : CoroutineScope {
        if (!isActive()) error("Already cleared!")
        return tags.getOrPut(key = key, supplier) as CoroutineScope
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        coroutineScope.launch(block = block)
    }

    internal fun clear() {
        synchronized(this) {
            if (!isActive()) error("Already cleared!")
            for ((_, it) in tags) {
                it.close()
            }
            tags.clear()
            isCleared = true
        }
    }
}
