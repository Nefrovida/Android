package com.example.nefrovida.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    // --- Temporary Hardcoded Token for Testing ---
    private val authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJhMzE3NzU5MC01MDNkLTQ3MDEtOGY5Yi1jZDFkZmI4ZDQzNzkiLCJyb2xlSWQiOjIsInByaXZpbGVnZXMiOlsiVklFV19BTkFMWVNJUyIsIkNSRUFURV9BTkFMWVNJUyIsIlVQREFURV9BTkFMWVNJUyIsIkRFTEVURV9BTkFMWVNJUyJdLCJpYXQiOjE3NjI5NzMxNjIsImV4cCI6MTc2MzU3Nzk2Mn0.sCP3ofOuIGLbiVByWJ0GQB3Y2wo88B00BhzyN9CKRlE"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", authToken)
            .build()

        return chain.proceed(newRequest)
    }
}
