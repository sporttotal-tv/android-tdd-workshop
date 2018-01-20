package de.jodamob.mytddapplication

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

class ObservableCountdown(val clock: Clock) {
    fun start(seconds: Int): Observable<Int> {
        return Observable.create(object: ObservableOnSubscribe<Int> {
            override fun subscribe(e: ObservableEmitter<Int>) {
                Coundown(clock).start(seconds, {e.onNext(it)})
            }
        })
    }
}
