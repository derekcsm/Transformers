package xyz.derekcsm.transformers.ui.create_transformer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_transformer.*
import kotlinx.android.synthetic.main.toolbar_button.*
import xyz.derekcsm.transformers.R
import xyz.derekcsm.transformers.base.Constants
import xyz.derekcsm.transformers.model.Transformer

@AndroidEntryPoint
class CreateTransformerActivity : AppCompatActivity(), CreateTransformerView {

    companion object {
        val EXTRA_TRANSFORMER_ID = "transformer_id"

        fun activityIntent(
            context: Context,
            transformerId: String?
        ): Intent? {
            val intent = Intent(context, CreateTransformerActivity::class.java)
            if (transformerId != null) {
                intent.putExtra(EXTRA_TRANSFORMER_ID, transformerId)
            }
            return intent
        }
    }

    private val TAG = "CreateTransformerActivity"

    @VisibleForTesting
    val viewModel by viewModels<CreateTransformerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.connectViewInterface(this)
        setContentView(R.layout.activity_create_transformer)

        btn_toolbar.text = getString(R.string.create)
        btn_toolbar.setOnClickListener {
            formatTransformerFromInputsAndCreate()
        }
    }

    private fun formatTransformerFromInputsAndCreate() {
        // todo
        if (et_name.text.toString().isEmpty()) {
            return
            // todo show error
        }

        val transformer = Transformer(
            "",
            et_name.text.toString(),
            Constants.TEAM_DECEPTICONS, // TODO add based on selection
            sb_strength.progress + 1,
            sb_intelligence.progress + 1,
            sb_speed.progress + 1,
            sb_endurance.progress + 1,
            sb_rank.progress + 1,
            sb_courage.progress + 1,
            sb_firepower.progress + 1,
            sb_skill.progress + 1,
            null
        )

        viewModel.postCreateTransformer(transformer)
    }

}