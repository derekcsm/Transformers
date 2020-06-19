package xyz.derekcsm.transformers.ui.transformers_list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_transformer.view.*
import kotlinx.android.synthetic.main.layout_item_stats.view.*
import xyz.derekcsm.transformers.R
import xyz.derekcsm.transformers.model.Transformer

class TransformerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val context = itemView.context

    fun bindView(listener: TransListAdapterListener, transformer: Transformer) {
        itemView.setOnClickListener {
            listener.onTransformerClicked(transformer)
        }

        itemView.tv_name.text = transformer.name
        itemView.sdv_team_icon.setImageURI(transformer.teamIcon)

        itemView.tv_courage.text = context.getString(
            R.string.stat,
            context.getString(R.string.courage),
            transformer.courage.toString()
        )
        itemView.tv_endurance.text = context.getString(
            R.string.stat,
            context.getString(R.string.endurance),
            transformer.endurance.toString()
        )
        itemView.tv_firepower.text = context.getString(
            R.string.stat,
            context.getString(R.string.firepower),
            transformer.firepower.toString()
        )
        itemView.tv_intelligence.text = context.getString(
            R.string.stat,
            context.getString(R.string.intelligence),
            transformer.intelligence.toString()
        )
        itemView.tv_rank.text = context.getString(
            R.string.stat,
            context.getString(R.string.rank),
            transformer.rank.toString()
        )
        itemView.tv_skill.text = context.getString(
            R.string.stat,
            context.getString(R.string.skill),
            transformer.skill.toString()
        )
        itemView.tv_speed.text = context.getString(
            R.string.stat,
            context.getString(R.string.speed),
            transformer.speed.toString()
        )
        itemView.tv_strength.text = context.getString(
            R.string.stat,
            context.getString(R.string.strength),
            transformer.strength.toString()
        )

    }

}