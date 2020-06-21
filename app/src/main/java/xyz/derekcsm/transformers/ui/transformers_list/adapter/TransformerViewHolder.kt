package xyz.derekcsm.transformers.ui.transformers_list.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.item_transformer.view.*
import kotlinx.android.synthetic.main.layout_item_stats.view.*
import xyz.derekcsm.transformers.R
import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.utils.ConnectivityUtils

class TransformerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val context = itemView.context
    lateinit var sdvTeamIcon: SimpleDraweeView

    fun bindView(listener: TransListAdapterListener, transformer: Transformer) {
        itemView.card_transformer.setOnClickListener {
            listener.onTransformerClicked(transformer)
        }
        itemView.tv_delete.setOnClickListener {
            if (ConnectivityUtils().isInternetAvailable(context)) {
                listener.onDeleteTransformerClicked(transformer)
            } else {
                Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            }
        }

        sdvTeamIcon = itemView.findViewById(R.id.sdv_team_icon)
        sdvTeamIcon.setImageURI(transformer.teamIcon)

        itemView.tv_name.text = transformer.name
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