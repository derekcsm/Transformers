package xyz.derekcsm.transformers.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class LiveCoroutinesViewModel : ViewModel() {

     val viewModelJob = SupervisorJob()
     val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    inline fun <T> launchOnViewModelScope(crossinline block: suspend () -> LiveData<T>): LiveData<T> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(block())
        }
    }

}