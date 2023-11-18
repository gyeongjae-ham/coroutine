package coroutine

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

// runBlocking : 일반루틴 세계와 코루틴 세계를 연결
fun main(): Unit = runBlocking {
    printWithThread("START")
    // launch : 반환값이 없는 코루틴 생성
    launch {
        newRoutine()
    }
    yield()
    printWithThread("END")
}

suspend fun newRoutine() {
    val num1 = 1
    val num2 = 2
    // yield : 코루틴을 중단하고 다른 코루틴이 실행되도록 한다
    yield()
    printWithThread("${num1 + num2}")
}

/**
 * 위 코드 순서
 * 1. runBlocking 안에서 println("START")을 실행한다
 * 2. launch로 새로운 코루틴을 실행한다
 * 3. launch 내부에서 yield로 다시 밖으로 나와서 println("END")을 실행한다
 * 4. launch의 println("${num1 + num2}")을 실행한다
 */

// coroutine은 스레드보다 context switching 비용이 낮을 수 있고, 하나의 스레드로도 동시성을 확보할 수 있다
// coroutine은 스스로 자리를 양보(yield)할 수 있다(비선점형)
// 동시성: 한 번에 한 가지 일만 할 수 있지만, 아주 빠르게 작업이 전환되어 동시에 하는 것처럼 보이는 것

fun printWithThread(str: Any) {
    println("[${Thread.currentThread().name}] $str")
}
