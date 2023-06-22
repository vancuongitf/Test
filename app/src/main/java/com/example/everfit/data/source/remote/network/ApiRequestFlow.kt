package com.example.everfit.data.source.remote.network

import com.example.everfit.data.source.remote.network.error.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import java.net.UnknownHostException

inline fun <T> requestFlow(crossinline block: suspend () -> Response<T>): Flow<ApiResponse<T>> =
    flow {
        try {
            val response = block.invoke()
            if (response.isSuccessful) {
                try {
                    emit(ApiResponse.success(response.body()!!))
                } catch (error: Exception) {
                    emit(
                        ApiResponse.error(
                            ApiException(
                                ApiException.ERROR_CODE_DATA_NULL, error.message ?: ""
                            )
                        )
                    )
                }
            } else {
                val message: String = try {
                    JSONObject(response.errorBody()?.string() ?: "").getString("message")
                } catch (error: Exception) {
                    "Have error please try again!"
                }
                emit(
                    ApiResponse.error(
                        ApiException(
                            response.code(), message
                        )
                    )
                )
            }
        } catch (error: Exception) {
            if (error is UnknownHostException) {
                emit(
                    ApiResponse.error(
                        ApiException(
                            ApiException.ERROR_CODE_NO_INTERNET,
                            "The requested data cannot be downloaded. There is no internet connection at the moment."
                        )
                    )
                )
            } else {
                emit(
                    ApiResponse.error(
                        ApiException.getUnKnowError()
                    )
                )
            }

        }
    }

inline fun <T> Flow<ApiResponse<T>>.subscribe(
    handleScope: CoroutineScope,
    crossinline doOnSubscribe: () -> Unit = {},
    crossinline doOnComplete: () -> Unit = {},
    crossinline onSuccess: (data: T) -> Unit = {},
    crossinline onError: (error: ApiException) -> Unit = {}
) {
    CoroutineScope(Dispatchers.IO).launch {
        handleScope.launch {
            doOnSubscribe.invoke()
        }
        collect { response ->
            handleScope.launch {
                when {
                    response.data != null -> {
                        onSuccess.invoke(response.data)
                    }

                    else -> {
                        onError.invoke(
                            response.error ?: ApiException.getUnKnowError()
                        )
                    }
                }
                doOnComplete.invoke()
            }
        }
    }
}
