package com.example.everfit.data.source.remote.network

import com.example.everfit.data.source.remote.network.error.ApiException

class ApiResponse<T>(val data: T? = null, val error: ApiException? = null) {

    companion object {

        fun <T> success(item: T) = ApiResponse(item)

        fun <T> error(error: ApiException) = ApiResponse<T>(null, error)
    }
}
