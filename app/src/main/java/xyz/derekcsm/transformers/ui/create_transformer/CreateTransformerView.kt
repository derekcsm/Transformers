package xyz.derekcsm.transformers.ui.create_transformer

interface CreateTransformerView {
    fun onRequestCompleted()
    fun showLoading()
    fun hideLoading()
}