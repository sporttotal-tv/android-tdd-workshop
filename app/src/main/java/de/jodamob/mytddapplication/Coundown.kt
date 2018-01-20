package de.jodamob.mytddapplication
interface Clock {
    fun sleep(milliseconds: Int)
}

class Coundown(val clock: Clock) {
    val MS_PER_SEC = 1000

    fun start(timeSeconds: Int, callback: (Int) -> Any) {
        var reaminingSeconds = timeSeconds
        while (reaminingSeconds-- > 0) {
            clock.sleep(MS_PER_SEC)
            callback(reaminingSeconds)
        }
    }

}