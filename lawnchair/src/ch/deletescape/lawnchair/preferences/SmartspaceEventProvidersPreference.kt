
package ch.deletescape.lawnchair.preferences

import android.content.Context
import androidx.preference.DialogPreference
import androidx.preference.PreferenceViewHolder
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import ch.deletescape.lawnchair.LawnchairPreferences
import ch.deletescape.lawnchair.lawnchairApp
import ch.deletescape.lawnchair.lawnchairPrefs
import ch.deletescape.lawnchair.runOnMainThread
import ch.deletescape.lawnchair.settings.ui.ControlledPreference
import ch.deletescape.lawnchair.smartspace.LawnchairSmartspaceController
import com.android.launcher3.R

class SmartspaceEventProvidersPreference(context: Context, attrs: AttributeSet?) :
        DialogPreference(context, attrs),
        ControlledPreference by ControlledPreference.Delegate(context, attrs),
        LawnchairPreferences.MutableListPrefChangeListener {

    private val providersPref = context.lawnchairPrefs.eventProviders

    init {
        updateSummary()
    }

    fun setProviders(providers: List<String>) {
        context.lawnchairPrefs.eventProviders.setAll(providers)
        context.lawnchairApp.smartspace.onProviderChanged()
    }

    private fun updateSummary() {
        val providerNames = providersPref.getAll()
                .map { LawnchairSmartspaceController.getDisplayName(context, it) }
        if (providerNames.isNotEmpty()) {
            summary = TextUtils.join(", ", providerNames)
        } else {
            setSummary(R.string.weather_provider_disabled)
        }
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)

        val summaryView = holder.findViewById(android.R.id.summary) as TextView
        summaryView.maxLines = 1
        summaryView.ellipsize = TextUtils.TruncateAt.END
    }

    override fun onAttached() {
        super.onAttached()
        providersPref.addListener(this)
    }

    override fun onDetached() {
        super.onDetached()
        providersPref.removeListener(this)
    }

    override fun onListPrefChanged(key: String) {
        runOnMainThread {
            updateSummary()
        }
    }

    override fun getDialogLayoutResource() = R.layout.dialog_preference_recyclerview
}
