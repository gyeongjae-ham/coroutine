package coroutine

import kotlinx.coroutines.*

// withTimeout / withTimeoutOrNull
// coroutineScope와 기본적으로 유사하지만, 주어진 시간 안에 새로 생긴 코루틴이 완료되어야 한다
fun main(): Unit = runBlocking {
    val result: Int? = withTimeoutOrNull(1_000L) {
        delay(1_500L)
        10 + 20
    }
    printWithThread(result!!)
}

fun lec08Example3(): Unit = runBlocking {
    printWithThread("START")
    printWithThread(calculateResult())
    printWithThread("END")
}

// withContext는 coroutineScope와 거의 동일한데, coroutine context에 더 설정하고 싶을 때 사용한다
suspend fun calculateResult2(): Int = withContext(Dispatchers.Default) {
    val num1 = async {
        delay(1_000L)
        10
    }

    val num2 = async {
        delay(1_000L)
        20
    }

    num1.await() + num2.await()
}

fun lec08Example2(): Unit = runBlocking {
    printWithThread("START")
    printWithThread(calculateResult())
    printWithThread("END")
}

// coroutineScope은 즉시 코루틴이 실행되고 결과가 반환된다
// 여러 코루틴을 엮어서 병렬적으로 구현하고 싶을 때 사용한다
suspend fun calculateResult(): Int = coroutineScope {
    val num1 = async {
        delay(1_000L)
        10
    }

    val num2 = async {
        delay(1_000L)
        20
    }

    num1.await() + num2.await()
}

/**
 * suspending function
 * 1. suspend가 붙은 함수
 * 2. 다른 suspend가 붙은 함수를 호출할 수 있다
 * 3. 코루틴이 중지 되었다가 재개될 수 있는 지점
 */
fun lec08Example1(): Unit = runBlocking {
    val result1 = call1()
    val result2 = call2(result1)

    printWithThread(result2)
}

suspend fun call1(): Int {
    return CoroutineScope(Dispatchers.Default).async {
        Thread.sleep(1_000L)
        100
    }.await()
}

suspend fun call2(num: Int): Int {
    return CoroutineScope(Dispatchers.Default).async {
        Thread.sleep(1_000L)
        200
    }.await()
}

interface AsyncCaller {
    suspend fun call()
}

class AsyncCallerImple : AsyncCaller {
    override suspend fun call() {
        TODO("Not yet implemented")
    }
}
