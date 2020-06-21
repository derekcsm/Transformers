package xyz.derekcsm.transformers.repository

import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.model.TransformersResponse
import xyz.derekcsm.transformers.network.ApiService
import xyz.derekcsm.transformers.persistence.TransformersDao
import javax.inject.Inject

class CreateEditTransformerRepository @Inject constructor(
    private val apiService: ApiService,
    private val transformersDao: TransformersDao
) : Repository() {

    private val TAG = "CreateEditRepository"

    fun getTransformerFromDB(id: String): Transformer {
        return transformersDao.getTransformer(id)
    }

    suspend fun createTransformer(errorEmitter: RemoteErrorEmitter, transformer: Transformer): Transformer? {

        val transformerResponse: Transformer? = safeApiCall(errorEmitter) {
            apiService.createTransformer(transformer)
        }

        transformerResponse?.let { transformersDao.upsert(it) }
        return transformerResponse
    }

    suspend fun updateTransformer(errorEmitter: RemoteErrorEmitter, transformer: Transformer): Transformer? {

        val transformerResponse: Transformer? = safeApiCall(errorEmitter) {
            apiService.updateTransformer(transformer)
        }

        transformerResponse?.let { transformersDao.upsert(it) }
        return transformerResponse
    }
}

data class CreateUpdateTransformerRepositoryResponse(
    var transformer: Transformer?,
    var errorMessage: String?
)
