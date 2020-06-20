package xyz.derekcsm.transformers.repository

import androidx.databinding.ObservableBoolean
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

    fun fetchTransformersFromDB(): List<Transformer> {
        return transformersDao.getTransformersList()
    }

    suspend fun fetchTransformersFromNetwork(): TransformersListRepositoryResponse {
        val transformersList: List<Transformer>

        val transformersResponse = apiService.getTransformers().await()
        return if (transformersResponse.isSuccessful) {
            isLoading.set(false)
            transformersList = transformersResponse.body()!!.transformers
            transformersDao.insertTransformersList(transformersList)
            TransformersListRepositoryResponse(transformersList, null)
        } else {
            isLoading.set(false)
            TransformersListRepositoryResponse(null, transformersResponse.message())
        }
    }

    suspend fun deleteTransformer(id: String): Boolean {
        transformersDao.deleteTransformer(id)

        val deleteResponse = apiService.deleteTransformer(id).await()
        return if (deleteResponse.isSuccessful) {
            isLoading.set(false)
            true
        } else {
            isLoading.set(false)
            false
        }
    }
}

data class TransformersListRepositoryResponse(
    var transformersList: List<Transformer>?,
    var errorMessage: String?
)