

package ch.deletescape.lawnchair.smartspace

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.annotation.Keep
import ch.deletescape.lawnchair.smartspace.LawnchairSmartspaceController.Line
import com.android.launcher3.R

@Keep
class BatteryStatusProvider(controller: LawnchairSmartspaceController) :
        LawnchairSmartspaceController.DataProvider(controller) {

    private val batteryReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent) {
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            charging = status == BatteryManager.BATTERY_STATUS_CHARGING
            full = status == BatteryManager.BATTERY_STATUS_FULL
            level = (100f
                     * intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                     / intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)).toInt()
            updateData(null, getEventCard())
        }
    }
    private var charging = false
    private var full = false
    private var level = 100

    init {
        context.registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    private fun getEventCard(): LawnchairSmartspaceController.CardData? {
        val lines = mutableListOf<Line>()
        when {
            full -> lines.add(Line(context, R.string.battery_full))
            charging -> lines.add(Line(context, R.string.battery_charging))
            level <= 15 -> lines.add(Line(context, R.string.battery_low))
            else -> return null
        }
        if (!full) {
            lines.add(Line("$level%"))
        }
        return LawnchairSmartspaceController.CardData(
                lines = lines,
                forceSingleLine = true)
    }

    override fun stopListening() {
        super.stopListening()
        context.unregisterReceiver(batteryReceiver)
    }
}
