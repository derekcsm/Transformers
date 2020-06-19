package xyz.derekcsm.transformers.ui.transformers_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xyz.derekcsm.transformers.R
import xyz.derekcsm.transformers.model.Transformer

class TransListAdapter(val listener: TransListAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var adapterItems = listOf<Transformer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TransformerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_transformer, parent, false)
        )
    }

    override fun getItemCount(): Int = adapterItems.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val transformerViewHolder = viewHolder as TransformerViewHolder
        transformerViewHolder.bindView(listener, adapterItems[position])
    }

    fun setItems(adapterItems: List<Transformer>) {
        this.adapterItems = adapterItems
        notifyDataSetChanged()
    }
}

interface TransListAdapterListener {
    fun onTransformerClicked(transformer: Transformer)
}