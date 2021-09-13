
package ch.deletescape.lawnchair.smartspace.accu

import android.text.TextUtils
import ch.deletescape.lawnchair.util.okhttp.OkHttpClientBuilder
import com.android.launcher3.BuildConfig
import com.android.launcher3.LauncherAppState
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object AccuRetrofitServiceFactory {
    private var ACCU_APIKEY: Pair<String, String> = Pair("apikey", BuildConfig.ACCUWEATHER_KEY)
    private val ACCU_BASE_URL = "https://api.accuweather.com"
    private val ACCU_DETAILS = Pair("details", "true")
    private val ACCU_GETPHOTOS = Pair("getPhotos", "true")
    private val ACCU_METRIC = Pair("metric", "true")
    private var okHttpClient: OkHttpClient? = null

    val accuWeatherRetrofitService: AccuWeatherRetrofitService by lazy { getAccuWeatherRetrofitService_() }

    val accuSearchRetrofitService: AccuSearchRetrofitService by lazy { getAccuSearchRetrofitService_() }

    fun setApiKey(apiKey: String) {
        if (!TextUtils.isEmpty(apiKey)) {
            ACCU_APIKEY = Pair("apiKey", apiKey)
        }
    }

    fun getAccuWeatherRetrofitService_(): AccuWeatherRetrofitService {
        return getRetrofitService(AccuWeatherRetrofitService::class.java)
    }

    fun getAccuSearchRetrofitService_(): AccuSearchRetrofitService {
        return getRetrofitService(AccuSearchRetrofitService::class.java)
    }

    private fun <T> getRetrofitService(serviceClass: Class<T>): T {
        val client = buildOkHttpClient()
        return Retrofit.Builder().baseUrl(ACCU_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build().create(serviceClass)
    }

    private fun buildOkHttpClient(): OkHttpClient? {
        if (okHttpClient == null) {
            synchronized(AccuRetrofitServiceFactory::class.java) {
                if (okHttpClient == null) {
                    okHttpClient = OkHttpClientBuilder().addQueryParam(ACCU_APIKEY).addQueryParam(ACCU_DETAILS).addQueryParam(ACCU_METRIC).build(LauncherAppState.getInstanceNoCreate().context)
                }
            }
        }
        return okHttpClient
    }
}