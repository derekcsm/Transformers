package xyz.derekcsm.transformers.ui.transformers_list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_transformer.view.*
import xyz.derekcsm.transformers.model.Transformer

class TransformerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(listener: TransListAdapterListener, transformer: Transformer) {
        itemView.tv_name.text = transformer.name
    }

}