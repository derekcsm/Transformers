package xyz.derekcsm.transformers.ui.create_transformer

import android.content.Context

interface CreateTransformerView {
    fun onRequestCompleted()
    fun showLoading()
    fun hideLoading()
    fun getContext() : Context
}