package sp.kx.logics

import kotlin.coroutines.CoroutineContext

internal class MockElement(override val key: CoroutineContext.Key<*>) : CoroutineContext.Element {}
