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

    suspend fun fetchTransformersFromNetwork(): TransformersRepositoryResponse {
        val transformersList: List<Transformer>

        when (val transformersResponse = apiService.getTransformers().await()) {
            is NetworkResponse.Success -> {
                isLoading.set(false)
                transformersList = transformersResponse.body.transformers
                transformersDao.insertTransformersList(transformersList)
                return TransformersRepositoryResponse(transformersList, null)
            }
            is NetworkResponse.ServerError -> {
                isLoading.set(false)
                return TransformersRepositoryResponse(null, transformersResponse.body!!.message())
            }
            is NetworkResponse.NetworkError -> {
                isLoading.set(false)
                return TransformersRepositoryResponse(null, transformersResponse.toString())
            }
            else -> {
                isLoading.set(false)
                error("unknown")
            }
        }
    }
}

data class TransformersRepositoryResponse(
    var transformersList: List<Transformer>?,
    var errorMessage: String?
)