package xyz.derekcsm.transformers.repository

import androidx.databinding.ObservableBoolean
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

    fun getTransformerFromDB(id: String): Transformer {
        return transformersDao.getTransformer(id)
    }

    suspend fun createTransformer(transformer: Transformer): CreateUpdateTransformerRepositoryResponse {
        val createTransformerResponse = apiService.createTransformer(transformer).await()
        return if (createTransformerResponse.isSuccessful) {
            createTransformerResponse.body()?.let { transformersDao.insertTransformer(it) }
            CreateUpdateTransformerRepositoryResponse(createTransformerResponse.body(), null)
        } else {
            CreateUpdateTransformerRepositoryResponse(null, createTransformerResponse.message())
        }
    }

    suspend fun updateTransformer(transformer: Transformer): CreateUpdateTransformerRepositoryResponse {
        val updateTransformerResponse = apiService.updateTransformer(transformer).await()
        return if (updateTransformerResponse.isSuccessful) {
            updateTransformerResponse.body()?.let { transformersDao.insertTransformer(it) }
            CreateUpdateTransformerRepositoryResponse(updateTransformerResponse.body(), null)
        } else {
            CreateUpdateTransformerRepositoryResponse(null, updateTransformerResponse.message())
        }
    }
}

data class CreateUpdateTransformerRepositoryResponse(
    var transformer: Transformer?,
    var errorMessage: String?
)
