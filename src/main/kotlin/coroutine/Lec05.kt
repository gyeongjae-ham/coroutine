package coroutine

import kotlinx.coroutines.*

// 예외를 try-catch-finally로 처리하는 방법이 있고,
// exception handler로 처리하는 방법이 있다
/**
 * CoroutineExceptionHandler 주의할 점
 * 1. launch에만 적용가능
 * 2. 부모 코루틴이 있으면 동작하지 않는다
 */
fun main(): Unit = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        printWithThread("예외")
    }

    val job = CoroutineScope(Dispatchers.Default).launch(exceptionHandler) {
        throw IllegalArgumentException()
    }

    delay(1_000L)
}

fun lec05Example5(): Unit = runBlocking {
    // SupervisorJob()을 쓰면, 예외를 부모에게 전파하지 않는다
    val job = async(SupervisorJob()) {
        throw IllegalArgumentException("예외가 발생했습니다")
    }

    delay(1_000L)
    job.await()
}

// 자식 코루틴의 예외는 부모에게 전파된다!
// 새로운 영역의 async가 아니라 자식 코루틴으로써의 async였기 때문에 바로 예외 발생
fun lec05Example4(): Unit = runBlocking {
    val job = async {
        throw IllegalArgumentException("예외가 발생했습니다")
    }

    delay(1_000L)
}

// async: 예외가 발생해도, 예외를 출력하지 않음. 예외를 확인하려면, await()가 필요함
fun lec05Example3(): Unit = runBlocking {
    val job = CoroutineScope(Dispatchers.Default).async {
        throw IllegalArgumentException("예외가 발생했습니다")
    }

    delay(1_000L)
    job.await()
}

// launch: 예외가 발생하면, 예외를 출력하고 코루틴이 종료
fun lec05Example2(): Unit = runBlocking {
    val job = CoroutineScope(Dispatchers.Default).launch {
        throw IllegalArgumentException("예외가 발생했습니다")
    }

    delay(1_000L)
}

// 새로운 root coroutine을 만들고 싶으면, 새로운 영역에 만들어야 한다
fun lec05Example1(): Unit = runBlocking {
    val job1 = CoroutineScope(Dispatchers.Default).launch {
        delay(1_000L)
        printWithThread("Job 1")
    }

    val job2 = CoroutineScope(Dispatchers.Default).launch {
        delay(1_000L)
        printWithThread("Job 2")
    }
}
