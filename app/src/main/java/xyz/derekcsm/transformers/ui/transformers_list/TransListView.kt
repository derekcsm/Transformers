package xyz.derekcsm.transformers.ui.transformers_list

import android.content.Context
import xyz.derekcsm.transformers.model.Transformer

interface TransListView {
    fun populateList(transformersList: List<Transformer>)
    fun getItemCount(): Int
    fun showLoading()
    fun hideLoading()
    fun getContext(): Context
}