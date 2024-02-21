package sp.kx.logics

object SimpleLogicsFactory : LogicsFactory {
    override fun <T : Logics> create(type: Class<T>): T {
        return type
            .getConstructor()
            .newInstance()
    }
}
