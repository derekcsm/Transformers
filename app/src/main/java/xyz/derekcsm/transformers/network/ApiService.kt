package xyz.derekcsm.transformers.network

import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.GET
import xyz.derekcsm.transformers.model.TransformersResponse

interface ApiService {

    @GET("allspark")
    fun getAuthentication(): Deferred<Response<String>>

    @GET("transformers")
    fun getTransformers(): Deferred<NetworkResponse<TransformersResponse, HttpException>>
}