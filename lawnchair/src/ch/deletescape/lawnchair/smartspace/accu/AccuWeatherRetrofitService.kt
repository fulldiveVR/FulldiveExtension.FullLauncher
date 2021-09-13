
package ch.deletescape.lawnchair.smartspace.accu

import ch.deletescape.lawnchair.smartspace.accu.model.AccuLocalWeatherGSon
import retrofit2.http.GET
import ch.deletescape.lawnchair.smartspace.accu.model.AccuHourlyForecastGSon
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query


interface AccuWeatherRetrofitService {
    @GET("forecasts/v1/hourly/24hour/{key}")
    fun getHourly(@Path("key") key: String, @Query("language") language: String): Call<List<AccuHourlyForecastGSon>>

    @GET("localweather/v1/{key}")
    fun getLocalWeather(@Path("key") key: String, @Query("language") language: String): Call<AccuLocalWeatherGSon>
}
