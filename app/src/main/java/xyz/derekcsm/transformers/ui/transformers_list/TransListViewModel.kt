package xyz.derekcsm.transformers.ui.transformers_list

import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import kotlinx.coroutines.launch
import xyz.derekcsm.transformers.R
import xyz.derekcsm.transformers.base.CoroutinesViewModel
import xyz.derekcsm.transformers.repository.ErrorType
import xyz.derekcsm.transformers.repository.RemoteErrorEmitter
import xyz.derekcsm.transformers.repository.TransformersRepository

class TransListViewModel @ViewModelInject constructor(
    private val transformersRepository: TransformersRepository
) : CoroutinesViewModel(), RemoteErrorEmitter {

    private val TAG = "TransListViewModel"

    var view: TransListView? = null
    fun connectViewInterface(view: TransListView) {
        this.view = view
    }

    init {
        Log.d(TAG, "initialized!")
    }

    fun deleteTransformer(id: String) {
        uiScope.launch {
            transformersRepository.deleteTransformer(this@TransListViewModel, id)
        }
    }

    fun fetchTransformers() {
        view!!.showLoading()
        view!!.populateList(transformersRepository.fetchTransformersFromDB())

        uiScope.launch {
            val response =
                transformersRepository.fetchTransformersFromNetwork(this@TransListViewModel)

            if (response != null) {
                view!!.populateList(response)
            }
            view!!.hideLoading()
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