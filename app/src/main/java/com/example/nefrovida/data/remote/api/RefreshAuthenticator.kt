package com.example.nefrovida.data.remote.api

import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route

class RefreshAuthenticator(
    private val refreshClient: OkHttpClient,
    private val refreshUrl: String
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // Avoid infinite loops - if we've already tried to refresh multiple times, give up
        if (responseCount(response) > 2) {
            return null
        }

        synchronized(this) {
            // Call refresh endpoint synchronously
            val refreshRequest = Request.Builder()
                .url(refreshUrl)
                .post(ByteArray(0).toRequestBody(null))
                .build()

            val refreshResponse = try {
                refreshClient.newCall(refreshRequest).execute()
            } catch (e: Exception) {
                null
            }

            // If refresh failed, return null (authentication failed)
            if (refreshResponse == null || !refreshResponse.isSuccessful) {
                return null
            }

            refreshResponse.close()

            // Refresh succeeded, cookies are now updated in the CookieJar
            // Retry the original request with the new cookies
            return response.request.newBuilder().build()
        }
    }

    private fun responseCount(response: Response): Int {
        var result = 1
        var priorResponse = response.priorResponse
        while (priorResponse != null) {
            result++
            priorResponse = priorResponse.priorResponse
        }
        return result
    }
}

