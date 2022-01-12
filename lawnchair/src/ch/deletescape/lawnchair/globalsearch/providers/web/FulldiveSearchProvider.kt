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

package ch.deletescape.lawnchair.globalsearch.providers.web

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import ch.deletescape.lawnchair.globalsearch.SearchProvider
import com.android.launcher3.R

class FulldiveSearchProvider(override val context: Context) : SearchProvider(context) {

    override val name: String = context.getString(R.string.search_provider_fulldive)
    override val supportsVoiceSearch = true
    override val supportsAssistant = false
    override val supportsFeed = false
    override val isAvailable: Boolean
        get() = isBrowserInstalled(context)

    override fun startSearch(callback: (intent: Intent) -> Unit) = callback(
            Intent(Intent.ACTION_MAIN).apply {
                component = ComponentName(
                        BROWSER_PACKAGE_NAME, CLASS_FLAT_ACTIVITY)
                putExtra(KEY_START_SCREEN_NAME, KEY_SEARCH_FRAGMENT)
            }
                                                                           )

    override fun startVoiceSearch(callback: (intent: Intent) -> Unit) = callback(
            Intent(Intent.ACTION_MAIN).apply {
                component = ComponentName(
                        BROWSER_PACKAGE_NAME, CLASS_FLAT_ACTIVITY)
                putExtra(KEY_START_SCREEN_NAME, KEY_SEARCH_FRAGMENT)
                putExtra(FIELD_NEED_OPEN_SPEECH_RECOGNITION, true)
            }
                                                                                )

    override fun getIcon(): Drawable = context.getDrawable(R.drawable.ic_fulldive_search)!!

    override fun getVoiceIcon(): Drawable = context.getDrawable(R.drawable.ic_widget_mic)!!

    private fun isBrowserInstalled(context: Context): Boolean {
        val app = try {
            context.packageManager.getApplicationInfo(BROWSER_PACKAGE_NAME, 0)
        } catch (e: Exception) {
            null
        }
        return app?.enabled ?: false
    }

    companion object {
        private const val BROWSER_PACKAGE_NAME = "com.fulldive.mobile"
        private const val CLASS_FLAT_ACTIVITY = "com.fulldive.evry.activities.FlatActivity"
        private const val FIELD_NEED_OPEN_SPEECH_RECOGNITION = "FIELD_NEED_OPEN_SPEECH_RECOGNITION"
        private const val KEY_START_SCREEN_NAME = "KEY_START_SCREEN_NAME"
        private const val KEY_SEARCH_FRAGMENT = "SearchFragment"
    }
}