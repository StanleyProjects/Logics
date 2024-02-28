package sp.kx.logics

import kotlin.coroutines.CoroutineContext

internal class MockCoroutineContext(
    private val elements: Map<out CoroutineContext.Key<out CoroutineContext.Element>, CoroutineContext.Element>,
) : CoroutineContext {
    override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R {
        return elements.values.fold(initial, operation)
    }

    override fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E {
        return elements[key] as E
    }

    override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext {
        error("Unsupported!")
    }
}
