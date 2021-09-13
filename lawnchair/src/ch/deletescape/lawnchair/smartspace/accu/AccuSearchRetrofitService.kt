
package ch.deletescape.lawnchair.smartspace.accu

import ch.deletescape.lawnchair.smartspace.accu.model.sub.AccuLocationGSon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AccuSearchRetrofitService {
    @GET("locations/v1/cities/autocomplete.json")
    fun getAutoComplete(@Query("q") query: String, @Query("language") language: String): Call<List<AccuLocationGSon>>

    @GET("locations/v1/cities/geoposition/search.json")
    fun getGeoPosition(@Query("q") query: String, @Query("language") language: String): Call<AccuLocationGSon>

    @GET("locations/v1/search")
    fun search(@Query("q") query: String, @Query("language") language: String): Call<List<AccuLocationGSon>>
}