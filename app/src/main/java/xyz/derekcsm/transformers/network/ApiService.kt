package xyz.derekcsm.transformers.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*
import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.model.TransformersResponse

interface ApiService {

    @GET("allspark")
    fun getAuthentication(): Deferred<Response<String>>

    @GET("transformers")
    suspend fun getTransformers(): TransformersResponse

    @POST("transformers")
    suspend fun createTransformer(@Body transformer: Transformer): Transformer

    @PUT("transformers")
    suspend fun updateTransformer(@Body transformer: Transformer): Transformer

    @DELETE("transformers/{transformerId}")
    suspend fun deleteTransformer(@Path("transformerId") id: String): Response<Any>
}