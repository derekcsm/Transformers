package xyz.derekcsm.transformers.ui.create_transformer

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_transformer.*
import kotlinx.android.synthetic.main.toolbar_button.*
import top.defaults.drawabletoolbox.DrawableBuilder
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

        toolbar.elevation = 0f
        btn_toolbar.text = getString(R.string.create)
        btn_toolbar.setOnClickListener {
            formatTransformerFromInputsAndCreate()
        }

        setupTeamListener()
    }

    private var selectedTeam: String = Constants.TEAM_AUTOBOTS
    private fun setupTeamListener() {

        // todo setup default
        selectTeam(Constants.TEAM_AUTOBOTS)

        tv_team_autobots.setOnClickListener {
            selectTeam(Constants.TEAM_AUTOBOTS)
        }

        tv_team_decepticons.setOnClickListener {
            selectTeam(Constants.TEAM_DECEPTICONS)
        }
    }

    private fun selectTeam(team: String) {
        selectedTeam = team
        var color: Int = 0
        when (selectedTeam) {
            Constants.TEAM_DECEPTICONS -> {
                color = ContextCompat.getColor(this, R.color.decepticon)
                toolbar.setBackgroundColor(color)
                ll_name.setBackgroundColor(color)
                updateStatusbarColor(color)
                tv_team_decepticons.setBackground(getTeamBackgroundDrawable())
                tv_team_autobots.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
            }
            Constants.TEAM_AUTOBOTS -> {
                color = ContextCompat.getColor(this, R.color.autobot)
                toolbar.setBackgroundColor(color)
                ll_name.setBackgroundColor(color)
                updateStatusbarColor(color)
                tv_team_autobots.setBackground(getTeamBackgroundDrawable())
                tv_team_decepticons.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
            }
        }
    }

    private fun getTeamBackgroundDrawable(): Drawable {
        var solidColor: Int = 0
        when (selectedTeam) {
            Constants.TEAM_DECEPTICONS -> {
                solidColor = ContextCompat.getColor(this, R.color.decepticon)
            }
            Constants.TEAM_AUTOBOTS -> {
                solidColor = ContextCompat.getColor(this, R.color.autobot)
            }
        }

        return DrawableBuilder()
            .rectangle()
            .rounded()
            .solidColor(solidColor)
            .ripple()
            .build()
    }

    private fun updateStatusbarColor(color: Int) {
        val window: Window = getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(color)
    }

    private fun formatTransformerFromInputsAndCreate() {

        if (et_name.text.toString().isEmpty()) {
            return
            // todo show error
        }

        val transformer = Transformer(
            "",
            et_name.text.toString(),
            selectedTeam,
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

    override fun onRequestCompleted() {
        super.onBackPressed()
    }
}