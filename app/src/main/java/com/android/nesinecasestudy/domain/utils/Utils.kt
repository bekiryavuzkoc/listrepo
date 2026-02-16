package com.android.nesinecasestudy.domain.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.nio.channels.UnresolvedAddressException

internal const val CONTENT_JSON = "application/json"
internal const val HEADER_CONTENT_TYPE = "Content-Type"
internal const val DEFAULT_CONNECT_TIME_OUT = 15000L
internal const val DEFAULT_READ_TIME_OUT = 15000L

const val CUSTOM_HEADER_CONNECT_TIMEOUT = "CONNECT_TIMEOUT"
const val CUSTOM_HEADER_READ_TIMEOUT = "READ_TIMEOUT"

internal fun Interceptor.Chain.setAdditionalSpecsAndProceed(
    requestBuilder: Request.Builder? = null
): Response {
    val rb = requestBuilder ?: request()
        .newBuilder()

    return proceed(rb.build())
}

suspend fun <T> handleApi(
    execute: suspend () -> retrofit2.Response<T>
): Result<T, NetworkError> {
    return try {
        val response = execute()
        val body = response.body()

        val errorMessage = try {
            response.errorBody()?.charStream()?.readText()?.let {
                JSONObject(it).optString("message")
            }
        } catch (_: Exception) {
            null
        }

        when (response.code()) {
            in 200..299 -> {
                if (body == null) Result.Error(NetworkError.SUCCESS_OTHER_THAN_200)
                else Result.Success(body)
            }

            400 -> Result.Error(NetworkError.BAD_REQUEST.apply { message = errorMessage })
            401 -> Result.Error(NetworkError.UNAUTHORIZED.apply { message = errorMessage })
            404 -> Result.Error(NetworkError.NOT_FOUND.apply { message = errorMessage })
            406 -> Result.Error(NetworkError.NOT_ACCEPTABLE.apply { message = errorMessage })
            409 -> Result.Error(NetworkError.CONFLICT.apply { message = errorMessage })
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT.apply { message = errorMessage })
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE.apply { message = errorMessage })
            422 -> Result.Error(NetworkError.UNPROCESSABLE_ENTITY.apply { message = errorMessage })
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR.apply { message = errorMessage })
            else ->  Result.Error(NetworkError.UNKNOWN.apply { message = errorMessage })
        }
    } catch (_: UnresolvedAddressException) {
        Result.Error(NetworkError.NO_INTERNET)
    } catch (e: Exception) {
        Log.e("handleApi", "Unexpected error: ${e.message}", e)
        Result.Error(NetworkError.UNKNOWN)
    }
}