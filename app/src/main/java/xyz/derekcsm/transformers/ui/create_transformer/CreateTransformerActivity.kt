package xyz.derekcsm.transformers.ui.create_transformer

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
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
    private var transformerId: String = ""

    @VisibleForTesting
    val viewModel by viewModels<CreateTransformerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.connectViewInterface(this)
        setContentView(R.layout.activity_create_transformer)

        if (intent.extras != null && intent.extras!!.containsKey(EXTRA_TRANSFORMER_ID)) {
            transformerId = intent!!.extras!!.get(EXTRA_TRANSFORMER_ID).toString()
        }

        if (transformerId != "") {
            /*
            Populate fields from existing Transformer for updating
             */
            val transformerToEdit = viewModel.getTransformerFromDB(transformerId)
            selectTeam(transformerToEdit.team)
            et_transformer_name.setText(transformerToEdit.name)
            sb_strength.setProgress(transformerToEdit.strength - 1)
            sb_intelligence.setProgress(transformerToEdit.intelligence - 1)
            sb_speed.setProgress(transformerToEdit.speed - 1)
            sb_endurance.setProgress(transformerToEdit.endurance - 1)
            sb_rank.setProgress(transformerToEdit.rank - 1)
            sb_courage.setProgress(transformerToEdit.courage - 1)
            sb_firepower.setProgress(transformerToEdit.firepower - 1)
            sb_skill.setProgress(transformerToEdit.skill - 1)
        }

        iv_close.setOnClickListener {
            super.onBackPressed()
        }
        toolbar.elevation = 0f
        btn_toolbar.text = getString(R.string.save)
        btn_toolbar.setOnClickListener {
            formatTransformerFromInputsAndCreate()
        }

        setupTeamListener()
    }

    private var selectedTeam: String = Constants.TEAM_AUTOBOTS
    private fun setupTeamListener() {

        // default for creation
        if (transformerId == "") {
            selectTeam(Constants.TEAM_AUTOBOTS)
        }

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

        if (et_transformer_name.text.toString().isEmpty()) {
            Toast.makeText(this, R.string.error_must_have_name, Toast.LENGTH_LONG).show()
            return
        }

        val transformer = Transformer(
            transformerId,
            et_transformer_name.text.toString(),
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

        if (transformerId != "") {
            viewModel.updateTransformer(transformer)
        } else {
            viewModel.createTransformer(transformer)
        }
    }

    override fun onRequestCompleted() {
        super.onBackPressed()
    }

    override fun showLoading() {
        btn_toolbar.isEnabled = false
        scrollview.deepForEach { isEnabled = false }
        progress_horizontal.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        btn_toolbar.isEnabled = true
        scrollview.deepForEach { isEnabled = true }
        progress_horizontal.visibility = View.GONE
    }

    fun ViewGroup.deepForEach(function: View.() -> Unit) {
        this.forEach { child ->
            child.function()
            if (child is ViewGroup) {
                child.deepForEach(function)
            }
        }
    }

    override fun getContext(): Context {
        return this
    }
}