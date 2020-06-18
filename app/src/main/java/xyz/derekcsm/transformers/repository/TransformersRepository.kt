package xyz.derekcsm.transformers.repository

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.network.ApiService
import xyz.derekcsm.transformers.persistence.TransformersDao
import javax.inject.Inject

class TransformersRepository @Inject constructor(
    private val apiService: ApiService,
    private val transformersDao: TransformersDao
) : Repository {

    private val TAG = "TransformersRepository"
    override var isLoading: ObservableBoolean = ObservableBoolean(false)

    suspend fun fetchTransformers(error: (String) -> Unit) = withContext(Dispatchers.IO) {
        Log.d(TAG, "fetchTransformers() called")
        val liveData = MutableLiveData<List<Transformer>>()
        isLoading.set(true)
        var transformersList = transformersDao.getTransformersList()

        when (val transformersResponse = apiService.getTransformers().await()) {
            is NetworkResponse.Success -> {
                isLoading.set(false)
                transformersList = transformersResponse.body.transformers
                liveData.postValue(transformersList)
                transformersDao.insertTransformersList(transformersList)
            }
            is NetworkResponse.ServerError -> {
                isLoading.set(false)
                error(transformersResponse.code)
            }
            is NetworkResponse.NetworkError -> {
                isLoading.set(false)
                error(transformersResponse.error)
            }
            else -> {
                isLoading.set(false)
                error("unknown")
            }
        }

        liveData.apply { postValue(transformersList) }
    }

    suspend fun loadTransformers() {
        when (val transformersResponse = apiService.getTransformers().await()) {
            is NetworkResponse.Success -> {
                isLoading.set(false)
//                transformersList = transformersResponse.body.transformers
//                liveData.postValue(transformersList)
//                transformersDao.insertTransformersList(transformersList)
            }
            is NetworkResponse.ServerError -> {
                isLoading.set(false)
//                error(transformersResponse.code)
            }
            is NetworkResponse.NetworkError -> {
                isLoading.set(false)
//                error(transformersResponse.error)
            }
            else -> {
                isLoading.set(false)
//                error("unknown")
            }
        }
    }
}