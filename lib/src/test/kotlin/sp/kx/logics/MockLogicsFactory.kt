package sp.kx.logics

internal class MockLogicsFactory<L : Logics>(
    private val logics: L,
) : LogicsFactory {
    override fun <T : Logics> create(type: Class<T>): T {
        check(type.isInstance(logics))
        return logics as T
    }
}
