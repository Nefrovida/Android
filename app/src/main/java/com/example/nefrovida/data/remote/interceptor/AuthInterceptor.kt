package com.example.nefrovida.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    // --- Temporary Hardcoded Token for Testing ---
    private val authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJhN2IyYmMxOC05OGIwLTQ3NDktOTk2ZC1iZjE1ODVmMTg1NTYiLCJyb2xlSWQiOjIsInByaXZpbGVnZXMiOlsiVklFV19BTkFMWVNJUyIsIkNSRUFURV9BTkFMWVNJUyIsIlVQREFURV9BTkFMWVNJUyIsIkRFTEVURV9BTkFMWVNJUyJdLCJpYXQiOjE3NjI4ODM2NDksImV4cCI6MTc2Mjk3MDA0OX0.ShwKHb2QUG361AgDLSrjd7w0AbAZIqP9j9I7ZGOWtBY"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", authToken)
            .build()

        return chain.proceed(newRequest)
    }
}
