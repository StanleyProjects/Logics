package sp.kx.logics

internal class MutableTags(tags: Map<String, Any>) {
    private val tags = tags.toMutableMap()

    fun <T : Any> getOrPut(key: String, supplier: () -> T): T {
        return synchronized(tags) {
            val previous = tags[key]
            if (previous == null) {
                val value = supplier()
                tags[key] = value
                value
            } else {
                previous as? T ?: TODO()
            }
        }
    }

    operator fun iterator(): Iterator<Map.Entry<String, Any>>{
        return tags.iterator()
    }

    fun clear() {
        tags.clear()
    }
}
