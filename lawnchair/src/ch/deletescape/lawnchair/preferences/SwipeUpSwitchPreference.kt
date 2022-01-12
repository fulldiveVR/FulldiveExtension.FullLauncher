/*
 *     Copyright (C) 2021 Lawnchair Team.
 *
 *     This file is part of Lawnchair Launcher.
 *
 *     Lawnchair Launcher is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Lawnchair Launcher is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Lawnchair Launcher.  If not, see <https://www.gnu.org/licenses/>.
 */

package ch.deletescape.lawnchair.preferences

import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.Switch
import ch.deletescape.lawnchair.applyColor
import ch.deletescape.lawnchair.getColorEngineAccent
import ch.deletescape.lawnchair.lawnchairPrefs
import ch.deletescape.lawnchair.settings.ui.search.SearchIndex
import ch.deletescape.lawnchair.util.extensions.d
import com.android.launcher3.Utilities
import com.android.quickstep.OverviewInteractionState
import com.android.systemui.shared.system.SettingsCompat

class SwipeUpSwitchPreference(context: Context, attrs: AttributeSet? = null) : StyledSwitchPreferenceCompat(context, attrs) {

    private val secureOverrideMode = OverviewInteractionState.isSwipeUpSettingsAvailable()
    private val hasWriteSecurePermission = Utilities.hasWriteSecureSettingsPermission(context)

    init {
        if (secureOverrideMode && !hasWriteSecurePermission) {
            isEnabled = false
        }
        isChecked = OverviewInteractionState.getInstance(context).isSwipeUpGestureEnabled
    }

    override fun onSetInitialValue(restoreValue: Boolean, defaultValue: Any?) {

    }

    override fun shouldDisableDependents(): Boolean {
        return disableDependentsState == isChecked
    }

    override fun persistBoolean(value: Boolean): Boolean {
        if (hasWriteSecurePermission && secureOverrideMode) {
            try {
                return Settings.Secure.putInt(context.contentResolver, securePrefName, if (value) 1 else 0)
            } catch (ignored: Exception) {
            }
        }
        return super.persistBoolean(value)
    }

    class SwipeUpSwitchSlice(context: Context, attrs: AttributeSet) : SwitchSlice(context, attrs) {

        private val secureOverrideMode = OverviewInteractionState.isSwipeUpSettingsAvailable()
        private val hasWriteSecurePermission = Utilities.hasWriteSecureSettingsPermission(context)

        override fun createSliceView(): View {
            return Switch(context).apply {
                applyColor(context.getColorEngineAccent())
                isChecked = OverviewInteractionState.getInstance(context).isSwipeUpGestureEnabled
                setOnCheckedChangeListener { _, isChecked ->
                    persistBoolean(isChecked)
                }
            }
        }

        private fun persistBoolean(value: Boolean): Boolean {
            if (hasWriteSecurePermission && secureOverrideMode) {
                try {
                    return Settings.Secure.putInt(context.contentResolver, securePrefName, if (value) 1 else 0)
                } catch (ignored: Exception) {
                }
            }
            context.lawnchairPrefs.swipeUpToSwitchApps = value
            return true
        }
    }

    companion object {

        private const val securePrefName = SettingsCompat.SWIPE_UP_SETTING_NAME

        @Keep
        @JvmStatic
        val sliceProvider = SearchIndex.SliceProvider.fromLambda(::SwipeUpSwitchSlice)
    }
}
