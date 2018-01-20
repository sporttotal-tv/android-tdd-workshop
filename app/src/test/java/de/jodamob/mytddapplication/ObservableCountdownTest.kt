package de.jodamob.mytddapplication

import io.reactivex.observers.TestObserver
import org.amshove.kluent.mock
import org.junit.Test

class ObservableCountdownTest {

    @Test
    fun `should pass over value`() {

        val observer = TestObserver<Int>()
        val observable = ObservableCountdown(mock<Clock>()).start(2)
        observable.subscribe(observer)

        observer.assertValues(1, 0)
    }
}