package sp.kx.logics

import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

internal class LogicsJob(context: CoroutineContext) : CoroutineContext by SupervisorJob() + context
