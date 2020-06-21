package xyz.derekcsm.transformers

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.hamcrest.Matcher

open class BaseTestRobot {

    fun clickView(resId: Int): ViewInteraction = Espresso.onView((ViewMatchers.withId(resId)))
        .perform(ViewActions.click())

    fun textView(resId: Int): ViewInteraction = Espresso.onView(ViewMatchers.withId(resId))

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
        .check(ViewAssertions.matches(ViewMatchers.withText(text)))

    fun matchText(resId: Int, text: String): ViewInteraction = matchText(textView(resId), text)

    fun typeTextIntoEditText(text: String, resId: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(resId)).perform(ViewActions.typeText(text))

    fun clearEditText(resId: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(resId)).perform(ViewActions.clearText())
}