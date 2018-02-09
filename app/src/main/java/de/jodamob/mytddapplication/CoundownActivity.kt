package de.jodamob.mytddapplication

import android.app.Activity
import android.databinding.Observable
import android.databinding.ObservableField
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_countdown.*
import toothpick.Toothpick
import javax.inject.Inject


open class CoundownActivity : Activity() {

    @Inject lateinit var viewmodel: CountdownViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        injectFields()
        time_value.text = viewmodel.counterString.get()
        start_button.setOnClickListener { viewmodel.start() }
        viewmodel.counterString.observe()
    }

    private fun ObservableField<String>.observe() {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(p0: Observable?, p1: Int) {
                    time_value.setText((p0 as ObservableField<String>).get())
                }
        })
    }

    private fun injectFields() {
        Toothpick.inject(this, Toothpick.openScope(""))
    }
}
