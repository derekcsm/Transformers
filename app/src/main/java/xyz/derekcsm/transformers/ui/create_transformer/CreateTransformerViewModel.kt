package xyz.derekcsm.transformers.ui.create_transformer

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import kotlinx.coroutines.launch
import xyz.derekcsm.transformers.base.CoroutinesViewModel
import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.repository.CreateEditTransformerRepository

class CreateTransformerViewModel @ViewModelInject constructor(
    private val createEditRepository: CreateEditTransformerRepository
) : CoroutinesViewModel() {

    private val TAG = "CreateTransViewModel"

    var view: CreateTransformerView? = null
    fun connectViewInterface(view: CreateTransformerView) {
        this.view = view
    }

    init {
        Log.d(TAG, "initialized!")
    }

    fun postCreateTransformer(transformer: Transformer) {
        uiScope.launch {
            val transformerResponse = createEditRepository.createTransformer(transformer)
            Log.d(TAG, "postCreateTransformer() called" + transformerResponse)
        }
    }

    fun putUpdateTransformer() {

    }
}