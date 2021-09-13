

package ch.deletescape.lawnchair

import android.content.Context
import androidx.annotation.Keep
import com.android.launcher3.LauncherAppState
import com.android.launcher3.config.FeatureFlags
import com.android.quickstep.OverviewCallbacks
import com.google.android.apps.nexuslauncher.PredictionUiStateManager

@Keep
class LawnchairOverviewCallbacks(private val context: Context) : OverviewCallbacks() {

    override fun onInitOverviewTransition() {
        super.onInitOverviewTransition()
        if (FeatureFlags.REFLECTION_FORCE_OVERVIEW_MODE) return
        PredictionUiStateManager.getInstance(context).switchClient(PredictionUiStateManager.Client.OVERVIEW)
    }

    override fun onResetOverview() {
        super.onResetOverview()
        if (FeatureFlags.REFLECTION_FORCE_OVERVIEW_MODE) return
        PredictionUiStateManager.getInstance(context).switchClient(PredictionUiStateManager.Client.HOME)
    }

    override fun closeAllWindows() {
        super.closeAllWindows()
        getActivity()?.let { launcher ->
            launcher.googleNow?.let { client ->
                if (launcher.isStarted && !launcher.isForceInvisible) {
                    client.hideOverlay(150)
                } else {
                    client.hideOverlay(false)
                }
            }
        }
    }

    private fun getActivity(): LawnchairLauncher? {
        return LauncherAppState.getInstanceNoCreate().model.callback as? LawnchairLauncher
    }
}
