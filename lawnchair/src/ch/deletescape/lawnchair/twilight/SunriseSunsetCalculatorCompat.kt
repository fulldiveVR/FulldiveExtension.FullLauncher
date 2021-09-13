
package ch.deletescape.lawnchair.twilight

import android.location.Location
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator
import com.luckycatlabs.sunrisesunset.dto.Location as SSLocation
import java.util.*

class SunriseSunsetCalculatorCompat(latitude: Double?, longitude: Double?, timeZone: TimeZone) {

    private val calculator = if (latitude != null && longitude != null)
        SunriseSunsetCalculator(SSLocation(latitude, longitude), timeZone)
    else null

    fun getOfficialSunriseCalendarForDate(calendar: Calendar): Calendar {
        return calculator?.getOfficialSunriseCalendarForDate(calendar) ?: cloneWithHour(calendar, 6)
    }

    fun getOfficialSunsetCalendarForDate(calendar: Calendar): Calendar {
        return calculator?.getOfficialSunsetCalendarForDate(calendar) ?: cloneWithHour(calendar, 20)
    }

    private fun cloneWithHour(src: Calendar, hour: Int): Calendar {
        return Calendar.getInstance(src.timeZone).apply {
            set(src.get(Calendar.YEAR), src.get(Calendar.MONTH), src.get(Calendar.DATE), hour, 0)
        }
    }
}
