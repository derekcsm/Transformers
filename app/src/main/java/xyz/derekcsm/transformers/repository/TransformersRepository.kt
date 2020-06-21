package xyz.derekcsm.transformers.repository

import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.model.TransformersResponse
import xyz.derekcsm.transformers.network.ApiService
import xyz.derekcsm.transformers.persistence.TransformersDao
import javax.inject.Inject

class TransformersRepository @Inject constructor(
    private val apiService: ApiService,
    private val transformersDao: TransformersDao
) : Repository() {

    private val TAG = "TransformersRepository"

    fun fetchTransformersFromDB(): List<Transformer> {
        return transformersDao.getTransformersList()
    }

    suspend fun fetchTransformersFromNetwork(errorEmitter: RemoteErrorEmitter): List<Transformer>? {
        var transformersList: List<Transformer> = listOf()

        val transformersListResponse: TransformersResponse? = safeApiCall(errorEmitter) {
            apiService.getTransformers()
        }

        if (transformersListResponse != null) {
            transformersList = transformersListResponse.transformers
            transformersDao.insertTransformersList(transformersList)
            return transformersList
        }

        return null
    }

    suspend fun deleteTransformer(errorEmitter: RemoteErrorEmitter, id: String) {
        transformersDao.deleteTransformer(id)
        safeApiCall(errorEmitter) {
            apiService.deleteTransformer(id)
        }
    }
}