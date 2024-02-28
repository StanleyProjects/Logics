package sp.kx.logics

import java.io.Closeable

abstract class Logics {
    private val tags: MutableSet<Closeable>

    constructor(tags: Set<Closeable>) {
        this.tags = tags.toMutableSet()
    }

    constructor() : this(tags = emptySet())

    internal fun clear() {
        for (it in tags) it.close()
        tags.clear()
    }
}
