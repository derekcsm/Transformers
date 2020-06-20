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
    fun getTransformers(): Deferred<Response<TransformersResponse>>

    @POST("transformers")
    fun createTransformer(@Body transformer: Transformer): Deferred<Response<Transformer>>

    @PUT("transformers")
    fun updateTransformer(@Body transformer: Transformer): Deferred<Response<Transformer>>

    @DELETE("transformers/{transformerId}")
    fun deleteTransformer(@Path("transformerId") id: String): Deferred<Response<String>>
}