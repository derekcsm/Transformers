package xyz.derekcsm.transformers.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

open class Repository {

    suspend inline fun <T> safeApiCall(
        emitter: RemoteErrorEmitter,
        crossinline responseFunction: suspend () -> T
    ): T? {
        return try {
            val response = withContext(Dispatchers.IO) { responseFunction.invoke() }
            response
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.printStackTrace()
                Log.e("ApiCalls", "Call error: ${e.localizedMessage}", e.cause)
                when (e) {
                    is HttpException -> {
                        emitter.onError(""+ e.code() + " Error")
                    }
                    is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                    is IOException -> emitter.onError(ErrorType.NETWORK)
                    else -> emitter.onError(ErrorType.UNKNOWN)
                }
            }
            null
        }
    }
}

interface RemoteErrorEmitter {
    fun onError(msg: String)
    fun onError(errorType: ErrorType)
}

enum class ErrorType {
    NETWORK, // IO
    TIMEOUT, // Socket
    UNKNOWN //Anything else
}
