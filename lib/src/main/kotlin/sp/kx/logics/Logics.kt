package sp.kx.logics

import java.io.Closeable

abstract class Logics {
    private val tags: MutableTags

    constructor(tags: Map<String, Any>) {
        this.tags = MutableTags(tags)
    }

    constructor() : this(tags = emptyMap())

    internal fun clear() {
        for ((_, tag) in tags) {
            if (tag !is Closeable) continue
            tag.close()
        }
        tags.clear()
    }
}
