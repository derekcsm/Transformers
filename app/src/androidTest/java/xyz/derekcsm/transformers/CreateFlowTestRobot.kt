package xyz.derekcsm.transformers

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import it.xabaras.android.espresso.recyclerviewchildactions.RecyclerViewChildActions.Companion.childOfViewAtPositionWithMatcher
import xyz.derekcsm.transformers.ui.transformers_list.adapter.TransformerViewHolder
import org.hamcrest.core.AllOf.allOf


open class CreateFlowTestRobot : BaseTestRobot() {

    fun pressCreate() {
        clickView(R.id.fab)
    }

    fun typeTransformerName(name: String) {
        clearEditText(R.id.et_transformer_name)
        typeTextIntoEditText(name, R.id.et_transformer_name)
    }

    fun saveTransformer() {
        clickView(R.id.btn_toolbar)
    }

    fun clickFirstTransformerInRecycler() {
        onView(withId(R.id.rv_transformers))
            .perform(RecyclerViewActions.actionOnItemAtPosition<TransformerViewHolder>(0, click()))
    }

    fun checkTransformerNameInRecyclerView(index: Int, name: String) {
        onView(withId(R.id.rv_transformers))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(index))
            .check(
                matches(
                    allOf(
                        childOfViewAtPositionWithMatcher(
                            R.id.tv_name,
                            index,
                            ViewMatchers.withText(name)
                        )
                    )
                )
            )
    }
}