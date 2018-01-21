package de.jodamob.mytddapplication

import android.app.onCreate
import android.widget.Button
import android.widget.TextView
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

// button start needs start countdown
// injects viewmodel
class CountdownActivityClassTest {

    val button = mock<Button>()
    val textView = mock<TextView>()
    val activity = spy(CoundownActivity()).apply {
        whenever(findViewById<TextView>(R.id.time_value)).thenReturn(textView)
        whenever(findViewById<Button>(R.id.start_button)).thenReturn(button)
    }

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

        activity.viewmodel.counter = 5

        verify(textView).setText("5")
    }
}