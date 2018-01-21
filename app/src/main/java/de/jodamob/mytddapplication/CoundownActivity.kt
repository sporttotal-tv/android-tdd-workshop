package de.jodamob.mytddapplication

import android.app.Activity
import android.databinding.Observable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Bundle
import android.widget.TextView

class CoundownActivity : Activity() {

    val viewmodel: CountdownViewModel = CountdownViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        val textView = findViewById<TextView>(R.id.time_value).apply {
            text = viewmodel.counterString.get()
        }

        viewmodel.counterString.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                textView.setText((p0 as ObservableField<String>).get())
            }
        })
    }

}