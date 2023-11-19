package coroutine

import kotlinx.coroutines.*

fun main() {
    CoroutineName("나만의 코루틴") + Dispatchers.Default
}

/**
 * CoroutineScope의 주요 역할
 * CoroutineContext라는 데이터를 보관하는 것
 * 코루틴과 관련된 여러가지 데이터를 가지고 있다
 * ex)
 * - CoroutineExceptionHandler
 * - 코루틴 이름
 * - 코루틴 그 자체
 * - CoroutineDispatcher
 */
suspend fun lec07Example1() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        delay(1_000L)
        printWithThread("Job 1")
    }

    job.join()
}

class AsyncLogic {
    private val scope = CoroutineScope(Dispatchers.Default)

    fun doSomething() {
        scope.launch {
            // 무언가 코루틴이 시작되어 작업!
        }
    }

    fun destroy() {
        scope.cancel()
    }

}
