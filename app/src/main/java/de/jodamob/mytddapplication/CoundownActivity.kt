package de.jodamob.mytddapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import toothpick.Toothpick
import toothpick.config.Module
import javax.inject.Inject

open class CoundownActivity : FragmentActivity() {

    @Inject lateinit var viewmodel: CountdownViewModel
    val counterText : TextView by lazy { findViewById<TextView>(R.id.time_value) }
    val startButton : Button by lazy { findViewById<Button>(R.id.start_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        injectFields()
        counterText.text = viewmodel.counterString.get()
        startButton.setOnClickListener { viewmodel.start() }
        viewmodel.counterString.observe()
    }

    private fun ObservableField<String>.observe() {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(p0: Observable?, p1: Int) {
                    counterText.setText((p0 as ObservableField<String>).get())
                }
        })
    }

    private fun injectFields() {
        val scope = Toothpick.openScope("")
        scope.installModules(Module().apply {
            bind(CountdownViewModel::class.java).toProviderInstance({
                ViewModelProviders.of(this@CoundownActivity).get(CountdownViewModel::class.java)
            })
        })
        Toothpick.inject(this, scope)
    }
}
