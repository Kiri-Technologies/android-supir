package com.kiri.common.utils

import org.json.JSONObject
import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<ApiResponse<T>>): Resource<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()?.dataData
                body?.let {
                    return Resource.success(body)
                }
                return Resource.success(null)
            }
            val jsonObj = JSONObject(response.errorBody()?.charStream()?.readText()!!)
            return error("${response.code()} ${jsonObj.getJSONObject("message")}")
        } catch (e: Exception) {
            return when {
                e.message?.contains("hostname") == true -> {
                    error("Terjadi Kesalahan")
                }
                e.message?.contains("401") == true -> {
                    error("Unauthorized")
                }
                else -> error(e.message ?: e.toString())
            }
        }
    }

    private fun <T> error(errorMessage: String): Resource<T> =
        Resource.error(errorMessage)
}
