package de.jodamob.mytddapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import toothpick.Toothpick
import toothpick.config.Module


@RunWith(AndroidJUnit4::class)
class CountdownActivityTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<CoundownActivity> = ActivityTestRule(CoundownActivity::class.java, false, false)

    var counter = 0
    val countingClock = object : Clock{
        override fun sleep(milliseconds: Int) {counter++}
    }
    val viewModel = CountdownViewModel(countingClock, Schedulers.trampoline(), Schedulers.trampoline())

    @Before
    fun setup() {
        Toothpick.openScope("").installTestModules(Module().apply {
            bind(CountdownViewModel::class.java).toInstance(viewModel)
        })

        activityRule.launchActivity(null)
    }

    @After
    fun tearDown() {
        Toothpick.reset()
    }

    @Test
    fun should_follow_viewmodel() {

        viewModel.counter = 8
        onView(withId(R.id.time_value)).check(matches(withText("8")))
    }

    @Test
    fun should_start_countdown() {
        onView(withId(R.id.start_button)).perform(click())
        assertThat(counter, `is`(10))
    }
}