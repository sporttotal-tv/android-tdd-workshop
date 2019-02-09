package de.jodamob.mytddapplication

import android.app.onCreate
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should be`
import org.junit.Test
import toothpick.Toothpick

class CountdownActivityInjectionTest {

    val button = mock<Button>()
    val textView = mock<TextView>()
    var activity = prepareForTest(object : CoundownActivity() {
        override fun <T : View?> findViewById(id: Int): T {
            return when(id) {
                R.id.time_value -> textView
                R.id.start_button -> button
                else -> null
            } as T
        }
    })

    @Test
    fun `should work`() {
        activity.onCreate(null)
    }

    @Test
    fun `should use arch component for rotation`() {
        activity.onCreate(null)

        activity.viewmodel `should be instance of` ViewModel::class
        val newInstance = Toothpick.openScope("").getInstance(CountdownViewModel::class.java)

        newInstance `should be` activity.viewmodel
    }
}