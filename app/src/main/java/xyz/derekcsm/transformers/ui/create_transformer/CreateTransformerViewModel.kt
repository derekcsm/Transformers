package xyz.derekcsm.transformers.ui.create_transformer

import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import kotlinx.coroutines.launch
import xyz.derekcsm.transformers.R
import xyz.derekcsm.transformers.base.CoroutinesViewModel
import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.repository.CreateEditTransformerRepository
import xyz.derekcsm.transformers.repository.ErrorType
import xyz.derekcsm.transformers.repository.RemoteErrorEmitter

class CreateTransformerViewModel @ViewModelInject constructor(
    private val createEditRepository: CreateEditTransformerRepository
) : CoroutinesViewModel(), RemoteErrorEmitter {

    private val TAG = "CreateTransViewModel"

    var view: CreateTransformerView? = null
    fun connectViewInterface(view: CreateTransformerView) {
        this.view = view
    }

    init {
        Log.d(TAG, "initialized!")
    }

    fun getTransformerFromDB(transformerId: String): Transformer {
        return createEditRepository.getTransformerFromDB(transformerId)
    }

    fun createTransformer(transformer: Transformer) {
        view!!.showLoading()
        uiScope.launch {
            val transformerResponse = createEditRepository.createTransformer(this@CreateTransformerViewModel, transformer)

            if (transformerResponse != null) {
                view!!.onRequestCompleted()
            } else {
                view!!.hideLoading()
            }
        }
    }

    fun updateTransformer(transformer: Transformer) {
        view!!.showLoading()
        uiScope.launch {
            val transformerResponse = createEditRepository.updateTransformer(this@CreateTransformerViewModel, transformer)

            if (transformerResponse != null) {
                view!!.onRequestCompleted()
            } else {
                view!!.hideLoading()
            }
        }
    }

    override fun onError(msg: String) {
        Toast.makeText(view!!.getContext(), msg, Toast.LENGTH_LONG).show()
    }

    override fun onError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.UNKNOWN -> {
                onError(view!!.getContext().getString(R.string.error_unknown))
            }
            ErrorType.TIMEOUT -> {
                onError(view!!.getContext().getString(R.string.error_timeout))
            }
            ErrorType.NETWORK -> {
                onError(view!!.getContext().getString(R.string.network_error))
            }
        }
    }
}