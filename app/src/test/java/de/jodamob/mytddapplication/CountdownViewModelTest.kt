package de.jodamob.mytddapplication

import android.databinding.Observable
import android.databinding.ObservableField
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertTrue
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.junit.After
import org.junit.Test
import java.util.*

class CountdownViewModelTest {

    val viewModel = CountdownViewModel(mock<Clock>(), observOn = Schedulers.trampoline())
    var disposable : Disposable? = null

    @After
    fun tearDown() {
        disposable?.dispose()
    }

    @Test
    fun `should have our start value`() {
        viewModel.counter `should be equal to` 10
    }

    @Test
    fun `should update`() {
        val steps = mutableListOf<String>()

        viewModel.counterString.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                steps.add((p0 as ObservableField<String>).get())
            }
        })
        disposable = viewModel.start()

        steps `should equal` listOf("9","8","7","6","5","4","3","2","1","0")
    }

    @Test
    fun `should use real clock`() {
        val viewModel = CountdownViewModel(subscribeOn = Schedulers.trampoline())
        viewModel.counter = 1

        val before = Date().time
        disposable = viewModel.start()
        val after = Date().time

        assertTrue(after > before + 1000)
    }

    @Test
    fun `should be non blocking`() {
        val viewModel = CountdownViewModel()
        disposable = viewModel.start()
        viewModel.counter `should be equal to` 10
    }
}
