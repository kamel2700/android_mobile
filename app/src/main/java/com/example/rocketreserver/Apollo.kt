package com.example.rocketreserver

import android.content.Context
import android.os.Looper
import com.apollographql.apollo.ApolloClient
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.riversun.okhttp3.OkHttp3CookieHelper


private var instance: ApolloClient? = null

fun apolloClient(context: Context): ApolloClient {
    check(Looper.myLooper() == Looper.getMainLooper()) {
        "Only the main thread can get the apolloClient instance"
    }

    if (instance != null) {
        return instance!!
    }
    val cookieHelper = OkHttp3CookieHelper()
    cookieHelper.setCookie(
        "https://norfoe.com/graphql",
        "access_token",
        User.getToken(context).toString()
    )

    val logInterceptor = HttpLoggingInterceptor()
    logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttpClient = OkHttpClient().newBuilder()
        .cookieJar(object : CookieJar {
            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {}
            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val oneCookie: ArrayList<Cookie> = ArrayList(1)
                oneCookie.add(createNonPersistentCookie(context))
                return oneCookie
            }
        })
        .addInterceptor(logInterceptor)
        .build()

    instance = ApolloClient.builder()
        .serverUrl("https://norfoe.com/graphql")
        .okHttpClient(okHttpClient)
        .build()

    return instance!!
}

fun createNonPersistentCookie(context: Context): Cookie {
    return Cookie.Builder()
        .domain("norfoe.com")
        .path("/")
        .name("access_token")
        .value("${User.getToken(context)}")
        .httpOnly()
        .secure()
        .build()
}