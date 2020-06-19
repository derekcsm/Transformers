package xyz.derekcsm.transformers.repository

import androidx.databinding.ObservableBoolean
import com.haroldadmin.cnradapter.NetworkResponse
import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.network.ApiService
import xyz.derekcsm.transformers.persistence.TransformersDao
import javax.inject.Inject

class CreateEditTransformerRepository @Inject constructor(
    private val apiService: ApiService,
    private val transformersDao: TransformersDao
) : Repository {

    private val TAG = "CreateEditRepository"
    override var isLoading: ObservableBoolean = ObservableBoolean(false)

    fun getTransformerFromDB(id: String): Transformer{
        return transformersDao.getTransformer(id)
    }

    suspend fun createTransformer(transformer: Transformer): CreateTransformerRepositoryResponse {
        when (val createTransformerResponse = apiService.createTransformer(transformer).await()) {
            is NetworkResponse.Success -> {
                isLoading.set(false)
                transformersDao.insertTransformer(createTransformerResponse.body)
                return CreateTransformerRepositoryResponse(createTransformerResponse.body, null)
            }
            is NetworkResponse.ServerError -> {
                isLoading.set(false)
                return CreateTransformerRepositoryResponse(
                    null,
                    createTransformerResponse.body!!.message()
                )
            }
            is NetworkResponse.NetworkError -> {
                isLoading.set(false)
                return CreateTransformerRepositoryResponse(
                    null,
                    createTransformerResponse.toString()
                )
            }
            else -> {
                isLoading.set(false)
                error("unknown")
            }
        }
    }
}

data class CreateTransformerRepositoryResponse(
    var transformer: Transformer?,
    var errorMessage: String?
)
