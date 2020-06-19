package xyz.derekcsm.transformers.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import xyz.derekcsm.transformers.base.Constants
import xyz.derekcsm.transformers.base.SharedPref
import xyz.derekcsm.transformers.di.NetworkModule
import kotlin.coroutines.CoroutineContext

class ApiAuthenticator(val context: Context, val sharedPref: SharedPref) : Authenticator,
    CoroutineScope {

    private val TAG = "ApiAuthenticator"
    private val RETRY_TAG = "transReqRetryCount"

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d(TAG, "authenticate() called with: route = $route, response = $response")

        val previousRetryCount = retryCount(response)

        if (previousRetryCount > 1) {
            return response.request
        }

        val newRequest = reAuthenticateRequestUsingRefreshToken(
            response.request,
            previousRetryCount + 1
        )

        return newRequest
    }

    private fun retryCount(response: Response): Int {
        return response.header(RETRY_TAG)?.toInt() ?: 0
    }

    @Synchronized
    private fun reAuthenticateRequestUsingRefreshToken(
        staleRequest: Request,
        retryCount: Int
    ): Request? {
        val authToken = renewToken().body()
        sharedPref.write(Constants.SHARED_PREF_TOKEN, authToken.toString())
        return rewriteRequest(staleRequest, retryCount, authToken.toString())
    }

    private fun renewToken() = runBlocking {
        val apiService = NetworkModule.buildAuthenticatorApiServiceInstance(context, sharedPref)
        apiService.getAuthentication().await()
    }

    private fun rewriteRequest(
        staleRequest: Request, retryCount: Int, authToken: String
    ): Request? {
        return staleRequest.newBuilder()
            .removeHeader("Authorization")
            .header("Authorization", "Bearer $authToken")
            .header(RETRY_TAG, "$retryCount")
            .build()
    }
}