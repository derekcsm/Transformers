package xyz.derekcsm.transformers.ui.transformers_list

import xyz.derekcsm.transformers.model.Transformer

interface TransListView {
    fun populateList(transformersList: List<Transformer>)
    fun getItemCount(): Int
}