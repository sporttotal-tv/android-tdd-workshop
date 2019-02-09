package de.jodamob.mytddapplication

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import android.view.View
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CountdownViewModel(
        val clock: Clock = SystemClock(),
        val observOn: Scheduler = AndroidSchedulers.mainThread(),
        val subscribeOn: Scheduler = Schedulers.computation())  : ViewModel() {

    var counter = 10
    set(value) {
        field = value
        counterString.set(value.toString())
    }
    val counterString = ObservableField<String>(counter.toString())

    fun start(): Disposable? {
        return ObservableCountdown(clock)
                .start(counter)
                .subscribeOn(subscribeOn)
                .observeOn(observOn)
                .subscribe { counter = it }
    }

    private class SystemClock() : Clock {
        override fun sleep(milliseconds: Int) = Thread.sleep(milliseconds.toLong())

    }
}
