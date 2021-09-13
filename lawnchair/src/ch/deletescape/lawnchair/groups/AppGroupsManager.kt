

package ch.deletescape.lawnchair.groups

import ch.deletescape.lawnchair.LawnchairPreferences
import ch.deletescape.lawnchair.util.extensions.d

class AppGroupsManager(val prefs: LawnchairPreferences) {

    var categorizationEnabled by prefs.BooleanPref("pref_appsCategorizationEnabled", false, ::onPrefsChanged)
    var categorizationType by prefs.EnumPref("pref_appsCategorizationType", CategorizationType.Tabs, ::onPrefsChanged)

    val drawerTabs by lazy { CustomTabs(this) }
    val flowerpotTabs by lazy { FlowerpotTabs(this) }
    val drawerFolders by lazy { DrawerFolders(this) }

    private fun onPrefsChanged() {
        prefs.getOnChangeCallback()?.let {
            drawerTabs.checkIsEnabled(it)
            flowerpotTabs.checkIsEnabled(it)
            drawerFolders.checkIsEnabled(it)
        }
    }

    fun getEnabledType(): CategorizationType? {
        return CategorizationType.values().firstOrNull { getModel(it).isEnabled }
    }

    fun getEnabledModel(): AppGroups<*>? {
        return CategorizationType.values().map { getModel(it) }.firstOrNull { it.isEnabled }
    }

    private fun getModel(type: CategorizationType): AppGroups<*> {
        return when (type) {
            CategorizationType.Flowerpot -> flowerpotTabs
            CategorizationType.Tabs -> drawerTabs
            CategorizationType.Folders -> drawerFolders
        }
    }

    enum class CategorizationType(val prefsKey: String) {

        Tabs("pref_drawerTabs"),
        Folders("pref_drawerFolders"),
        Flowerpot("pref_drawerFlowerpot")
    }
}
