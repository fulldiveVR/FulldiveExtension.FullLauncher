

package ch.deletescape.lawnchair

import android.content.Context
import android.util.TypedValue
import ch.deletescape.lawnchair.colors.PixelAccentResolver
import ch.deletescape.lawnchair.globalsearch.providers.GoogleSearchProvider
import ch.deletescape.lawnchair.util.SingletonHolder
import com.android.launcher3.R

class LawnchairConfig(context: Context) {

    val defaultEnableBlur = context.resources.getBoolean(R.bool.config_default_enable_blur)
    val defaultBlurStrength = TypedValue().apply {
        context.resources.getValue(R.dimen.config_default_blur_strength, this, true)
    }.float
    val defaultIconPacks = context.resources.getStringArray(R.array.config_default_icon_packs) ?: emptyArray()
    val enableLegacyTreatment = context.resources.getBoolean(R.bool.config_enable_legacy_treatment)
    val enableColorizedLegacyTreatment = context.resources.getBoolean(R.bool.config_enable_colorized_legacy_treatment)
    val enableWhiteOnlyTreatment = context.resources.getBoolean(R.bool.config_enable_white_only_treatment)
    val hideStatusBar = context.resources.getBoolean(R.bool.config_hide_statusbar)
    val enableSmartspace = context.resources.getBoolean(R.bool.config_enable_smartspace)
    val defaultSearchProvider: String = context.resources.getString(
            R.string.config_default_search_provider) ?: GoogleSearchProvider::class.java.name
    val defaultColorResolver: String = context.resources.getString(
            R.string.config_default_color_resolver) ?: PixelAccentResolver::class.java.name

    companion object : SingletonHolder<LawnchairConfig, Context>(
            ensureOnMainThread(useApplicationContext(::LawnchairConfig)))
}
