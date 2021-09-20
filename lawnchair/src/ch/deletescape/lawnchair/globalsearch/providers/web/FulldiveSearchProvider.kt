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