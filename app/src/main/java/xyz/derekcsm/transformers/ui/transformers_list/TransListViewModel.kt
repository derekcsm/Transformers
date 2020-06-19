package xyz.derekcsm.transformers.ui.transformers_list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import kotlinx.coroutines.launch
import xyz.derekcsm.transformers.base.CoroutinesViewModel
import xyz.derekcsm.transformers.repository.TransformersRepository

class TransListViewModel @ViewModelInject constructor(
    private val transformersRepository: TransformersRepository
) : CoroutinesViewModel() {

    private val TAG = "TransListViewModel"

    var view: TransListView? = null
    fun connectViewInterface(view: TransListView) {
        this.view = view
    }

    init {
        Log.d(TAG, "initialized!")
    }

    fun fetchTransformers() {
        uiScope.launch {
            val response = transformersRepository.fetchTransformersFromNetwork()
            if (response.transformersList != null) {
                view!!.populateList(response.transformersList!!)
            } else {
                // handle error
            }
        }
    }


}