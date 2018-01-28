package de.jodamob.mytddapplication

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Button
import android.widget.TextView
import de.jodamob.mytddapplication.databinding.ActivityCountdownBinding
import toothpick.Toothpick
import toothpick.config.Module
import javax.inject.Inject

open class CoundownActivity : FragmentActivity() {

    @Inject lateinit var viewmodel: CountdownViewModel
    @Inject lateinit var databinding: DataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFields()
        databinding.bind(viewmodel, R.layout.activity_countdown)
    }

    private fun injectFields() {
        val scope = Toothpick.openScope("")
        scope.installModules(Module().apply {
            bind(CountdownViewModel::class.java).toProviderInstance({
                ViewModelProviders.of(this@CoundownActivity).get(CountdownViewModel::class.java)
            })
            bind(DataBinding::class.java).toInstance(object: DataBinding{
                override fun bind(viewModel: CountdownViewModel, layout: Int) {
                    DataBindingUtil.setContentView<ActivityCountdownBinding>(this@CoundownActivity, layout).apply {
                        viewmodel = viewModel
                    }
                }
            })
        })
        Toothpick.inject(this, scope)
    }
}
