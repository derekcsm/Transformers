package xyz.derekcsm.transformers.network

import okhttp3.Interceptor
import okhttp3.Response
import xyz.derekcsm.transformers.base.Constants
import xyz.derekcsm.transformers.base.SharedPref
import java.io.IOException

class RequestInterceptor(private val sharedPref: SharedPref) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Content-Type", "application/json")

        val token = sharedPref.read(Constants.SHARED_PREF_TOKEN, "")
        builder.addHeader("Authorization", "Bearer " + token)

        return chain.proceed(builder.build())
    }
}