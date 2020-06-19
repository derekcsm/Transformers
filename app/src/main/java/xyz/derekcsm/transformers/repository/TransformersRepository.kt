package xyz.derekcsm.transformers.repository

import androidx.databinding.ObservableBoolean
import com.haroldadmin.cnradapter.NetworkResponse
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

        when (val transformersResponse = apiService.getTransformers().await()) {
            is NetworkResponse.Success -> {
                isLoading.set(false)
                transformersList = transformersResponse.body.transformers
                transformersDao.insertTransformersList(transformersList)
                return TransformersListRepositoryResponse(transformersList, null)
            }
            is NetworkResponse.ServerError -> {
                isLoading.set(false)
                return TransformersListRepositoryResponse(
                    null,
                    transformersResponse.body!!.message()
                )
            }
            is NetworkResponse.NetworkError -> {
                isLoading.set(false)
                return TransformersListRepositoryResponse(null, transformersResponse.toString())
            }
            else -> {
                isLoading.set(false)
                error("unknown")
            }
        }
    }

    suspend fun deleteTransformer(id: String): Boolean {
        transformersDao.deleteTransformer(id)

        when (val deleteResponse = apiService.deleteTransformer(id).await()) {
            is NetworkResponse.Success -> {
                isLoading.set(false)
                return true
            }
            is NetworkResponse.ServerError -> {
                isLoading.set(false)
                return false
            }
            is NetworkResponse.NetworkError -> {
                isLoading.set(false)
                return false
            }
            else -> {
                isLoading.set(false)
                error("unknown")
            }
        }
    }
}

data class TransformersListRepositoryResponse(
    var transformersList: List<Transformer>?,
    var errorMessage: String?
)