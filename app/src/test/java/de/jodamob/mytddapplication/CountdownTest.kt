package de.jodamob.mytddapplication

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class CountdownTest {

    val clock = mock<Clock>()
    val coundown = Coundown(clock)


    @Test
    fun `should do nothing on zero`() {
        coundown.start(0) {}

        verifyZeroInteractions(clock)
    }

    @Test
    fun `should wait one second`() {
        coundown.start(1) {}
        verify(clock).sleep(1000)
    }

    @Test
    fun `should wait twice`() {
        coundown.start(2) {}
        verify(clock, times(2)).sleep(1000)
    }

    @Test
    fun `should report`() {

        val list = mutableListOf<Int>()

        coundown.start(2, {list.add(it)})

        list.size `should be equal to` 2
        list[0] `should be equal to` 1
        list[1] `should be equal to` 0
    }
}