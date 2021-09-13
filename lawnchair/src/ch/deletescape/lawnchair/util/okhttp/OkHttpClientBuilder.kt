

package ch.deletescape.lawnchair.util.okhttp

import android.content.Context
import ch.deletescape.lawnchair.lawnchairPrefs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class OkHttpClientBuilder {
    private val builder = OkHttpClient.Builder()
    private val queryParams = mutableMapOf<String, String>()

    fun addQueryParam(param: Pair<String, String>): OkHttpClientBuilder {
        queryParams.putAll(arrayOf(param))
        return this
    }

    fun build(context: Context?): OkHttpClient {
        if (queryParams.isNotEmpty()) {
            builder.addInterceptor {
                val urlBuilder = it.request().url.newBuilder()
                for (param in queryParams) {
                    urlBuilder.addQueryParameter(param.key, param.value)
                }
                it.proceed(it.request().newBuilder().url(urlBuilder.build()).build())
            }
        }
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            level = if (context?.lawnchairPrefs?.debugOkHttp == true) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
        })
        return builder.build()
    }
}