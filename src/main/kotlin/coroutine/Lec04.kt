package coroutine

import kotlinx.coroutines.*

// delay 같은 suspend 함수도 내부적으로 CancellationException으로 처리하는 거라서
// 해당 Exception을 잡으면 취소가 안됩니다
fun main(): Unit = runBlocking {
    val job = launch {
        try {
            delay(1_000L)
        } catch (e: CancellationException) {
            // 아무것도 안한다!!
        }

        printWithThread("delay에 의해 취소되지 않았다!!")
    }

    delay(100L)
    printWithThread("취소 시작")
    job.cancel()
}

/**
 * 취소에 협조하는 방법 2
 * isActive: 현재 코루틴이 활성화 되어 있는지, 취소 신호를 받았는지 확인
 * Dispatchers.Default: 코루틴을 다른 스레드에 배정
 */
fun lec04Example2(): Unit = runBlocking {
    val job = launch(Dispatchers.Default) {
        var i = 1
        var nextPrintTime = System.currentTimeMillis()
        while (i <= 5) {
            if (nextPrintTime <= System.currentTimeMillis()) {
                printWithThread("${i++}번째 출력!")
                nextPrintTime += 1_000L
            }

            if (!isActive) {
                throw CancellationException()
            }
        }
    }

    delay(100L)
    job.cancel()
}

/**
 * 취소에 협조하는 방법 1
 * delay(), yield() 같은 kotlinx.coroutines 패키지의 suspend 함수 사용
 */
fun lec04Example1(): Unit = runBlocking {
    val job1 = launch {
        delay(1_000L)
        printWithThread("Job 1")
    }

    val job2 = launch {
        delay(1_000L)
        printWithThread("Job 2")
    }

    delay(100)
    job1.cancel()
}
