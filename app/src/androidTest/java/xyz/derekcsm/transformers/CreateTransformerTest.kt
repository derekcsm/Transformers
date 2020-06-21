package xyz.derekcsm.transformers

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.derekcsm.transformers.ui.transformers_list.TransListActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class CreateTransformerTest : CreateFlowTestRobot() {

    @get:Rule
    var activityRule: ActivityTestRule<TransListActivity> =
        ActivityTestRule(TransListActivity::class.java)

    @Test
    fun test_create_transformer_1() {
        val transformerName = "Transformer-" + (0..10000).random()
        pressCreate()
        typeTransformerName(transformerName)
        saveTransformer()
    }

    @Test
    fun test_edit_transformer_1() {
        val transformerName = "Transformer-" + (0..10000).random() + "-Edited"
        clickFirstTransformerInRecycler()
        typeTransformerName(transformerName)
        saveTransformer()
        checkTransformerNameInRecyclerView(0, transformerName)
    }
}