package xyz.derekcsm.transformers.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import xyz.derekcsm.transformers.base.LiveCoroutinesViewModel
import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.repository.TransformersRepository

class TransListViewModel @ViewModelInject constructor(
    private val transformersRepository: TransformersRepository
) : LiveCoroutinesViewModel() {

    private val TAG = "TransListViewModel"

    private var transformersFetchingLiveData: MutableLiveData<String> = MutableLiveData()
    val transformersLiveData: LiveData<List<Transformer>>

    init {
        Log.d(TAG, "initialized!")
        transformersLiveData = launchOnViewModelScope {
            this.transformersRepository.fetchTransformers { onRequestError(it) }
        }

    }

    fun fetchTransformers() {
        uiScope.launch { transformersRepository.loadTransformers() }
    }

    private fun onRequestError(message: String) {
        Log.d(TAG, "onRequestError() called with: message = $message")
    }

}