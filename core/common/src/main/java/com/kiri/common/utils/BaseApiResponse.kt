package com.kiri.common.utils

import com.kiri.common.BuildConfig
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
            }
            val jsonObj = JSONObject(response.errorBody()?.charStream()?.readText()!!)
            return error("${response.code()} ${jsonObj.getJSONObject("message")}")
        } catch (e: Exception) {
            return if (e.message?.contains("hostname") == true) {
                error("Terjadi Kesalahan")
            } else {
                error(e.message ?: e.toString())
            }
        }
    }

    private fun <T> error(errorMessage: String): Resource<T> =
        Resource.error(errorMessage)
}
