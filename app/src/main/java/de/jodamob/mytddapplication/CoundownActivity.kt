package de.jodamob.mytddapplication

import android.app.Activity
import android.databinding.Observable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import toothpick.Toothpick
import javax.inject.Inject

class CoundownActivity : Activity() {

    @Inject lateinit var viewmodel: CountdownViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        injectFields()

        val textView = findViewById<TextView>(R.id.time_value).apply {
            text = viewmodel.counterString.get()
        }

        findViewById<Button>(R.id.start_button).setOnClickListener {
            viewmodel.start()
        }

        viewmodel.counterString.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                textView.setText((p0 as ObservableField<String>).get())
            }
        })
    }

    private fun injectFields() {
        Toothpick.inject(this, Toothpick.openScope(""))
    }
}
