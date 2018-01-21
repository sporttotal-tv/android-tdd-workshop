package de.jodamob.mytddapplication

import android.app.onCreate
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.schedulers.Schedulers
import org.amshove.kluent.`should be instance of`
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import toothpick.testing.ToothPickRule

class CountdownActivityTest {

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

    @JvmField
    @Rule
    val testrule = ToothPickRule(this,"")

    val clock = mock<Clock>()
    @Mock val viewmodel = CountdownViewModel(clock, Schedulers.trampoline(), Schedulers.trampoline())

    @Test
    fun `inflates layout`() {
        activity.onCreate(null)
        verify(activity).setContentView(R.layout.activity_countdown)
    }

    @Test
    fun `has starting number`() {
        activity.onCreate(null)
        verify(textView).setText("10")
    }

    @Test
    fun `updates number`() {
        activity.onCreate(null)

        viewmodel.counter = 5

        verify(textView).setText("5")
    }

    @Test
    fun `starts countdown on button click`() {

        activity.onCreate(null)

        argumentCaptor<View.OnClickListener>().apply {
            verify(button).setOnClickListener(capture())
            firstValue.onClick(button)
        }

        verify(clock, times(10)).sleep(1000)
    }

    @Test
    fun `is app compat`() {
        activity `should be instance of` FragmentActivity::class
    }
}
