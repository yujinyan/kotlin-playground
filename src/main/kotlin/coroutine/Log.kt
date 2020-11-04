package coroutine

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")