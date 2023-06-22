package com.example.everfit.data.source.remote.network.error

class ApiException(val code: Int, errorMessage: String) : Throwable(errorMessage) {

    companion object {
        const val ERROR_CODE_DATA_NULL: Int = 600
        const val ERROR_CODE_UNKNOWN_ERROR: Int = 601
        const val ERROR_CODE_NO_INTERNET: Int = 602

        fun getUnKnowError() =
            ApiException(ERROR_CODE_UNKNOWN_ERROR, "Have error please try again!")
    }
}
