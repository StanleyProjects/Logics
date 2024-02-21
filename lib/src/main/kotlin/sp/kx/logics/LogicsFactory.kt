package sp.kx.logics

interface LogicsFactory {
    fun <T : Logics> create(type: Class<T>): T
}
