package sp.kx.logics

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.Closeable
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class Logics {
    private val coroutineContext: CoroutineContext
    private val tags: MutableMap<String, Closeable>
    private var isCleared = false

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

    protected fun <T> getCoroutineScope(
        key: String,
        supplier: () -> T,
    ): CoroutineScope where T : Closeable, T : CoroutineScope {
        if (isCleared) error("Already cleared!")
        return tags.getOrPut(key = key, supplier) as CoroutineScope
    }

    protected val coroutineScope: CoroutineScope
        get() {
            return getCoroutineScope(
                key = "${this::class.java.name}:COROUTINE_SCOPE",
                supplier = { CloseableCoroutineScope(SupervisorJob() + coroutineContext) },
            )
        }

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        coroutineScope.launch(block = block)
    }

    internal fun clear() {
        synchronized(this) {
            if (isCleared) error("Already cleared!")
            for ((_, it) in tags) {
                it.close()
            }
            tags.clear()
            isCleared = true
        }
    }
}
