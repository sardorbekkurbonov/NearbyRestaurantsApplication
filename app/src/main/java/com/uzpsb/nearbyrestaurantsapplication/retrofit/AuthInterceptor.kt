package com.uzpsb.nearbyrestaurantsapplication.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val originalHttpUrl = originalRequest.url

        val authUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(CLIENT_ID_KEY, CLIENT_ID)
            .addQueryParameter(CLIENT_SECRET_KEY, CLIENT_SECRET)
            .build()

        val authRequest = originalRequest.newBuilder()
            .addHeader("Authorization","fsq3m3q/UtEPPhQanSfjzz/f2G9eoADCUjMuw2B3xcm3xUs=")
            .addHeader("Content-Type","application/json")
            .url(authUrl)
            .build()

        return chain.proceed(authRequest)
    }

    companion object {
        private const val CLIENT_ID_KEY = "client_id"
        private const val CLIENT_ID = "2WJX2XOGEVAIIGJYLYG4YXUI44PVN2FFXSAY4SJJXLG1WFY4"
        private const val CLIENT_SECRET_KEY = "client_secret"
        private const val CLIENT_SECRET = "YAQCUXSZJAURQZUKNFHBAXKEXS0Y0RDI353UH3YRB5CGJD5M"

    }
}