package coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class Lec03

fun main(): Unit = runBlocking {
    // async의 장점: callback을 이용하지 않고 동기 방식으로 코드를 작성할 수 있다
    val time = measureTimeMillis {
        val job1 = async { apiCall1() }
        val job2 = async { apiCall2(job1.await()) }
        printWithThread(job2.await())
    }

    printWithThread("소요 시간 : $time ms")
}

suspend fun apiCall1(): Int {
    delay(1_000L)
    return 1
}

suspend fun apiCall2(num: Int): Int {
    delay(1_000L)
    return 2 + num
}

fun example5(): Unit = runBlocking {
    // async: 주어진 함수의 결과를 반환할 수 있다(거의 launch랑 비슷)
    val job = async {
        3 + 5
    }
    val eight = job.await() // await : async의 결과를 가져오는 함수
    printWithThread(eight)
}

fun example4(): Unit = runBlocking {
    val job1 = launch {
        delay(1_000)
        printWithThread("Job 1")
    }
    // 코루틴 완료까지 기다림
    job1.join()

    val job2 = launch {
        delay(1_000)
        printWithThread("Job 2")
    }
}

fun example3(): Unit = runBlocking {
    val job = launch {
        (1..5).forEach {
            printWithThread(it)
            delay(500)
        }
    }

    delay(1_000L)
    // 코루틴 종료
    job.cancel()
}

fun example2(): Unit = runBlocking {
    // launch: 반환이 없는 코루틴을 생성
    // job은 launch의 반환값이 아닌 코루틴을 제어할 수 있는 객체
    val job = launch {
        printWithThread("Hello launch")
    }

    delay(1_000L)
    // 코루틴 시작
    job.start()
}

suspend fun example1() {
    runBlocking {
        printWithThread("START")
        launch {
            delay(2_000L)
            printWithThread("LAUNCH END")
        }
    }

    printWithThread("END")
}
