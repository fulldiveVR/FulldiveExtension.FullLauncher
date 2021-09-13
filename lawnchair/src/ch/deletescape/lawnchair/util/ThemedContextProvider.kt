

package ch.deletescape.lawnchair.util

import android.content.Context
import android.view.ContextThemeWrapper
import ch.deletescape.lawnchair.theme.ThemeManager
import ch.deletescape.lawnchair.theme.ThemeOverride

class ThemedContextProvider(private val base: Context, var listener: Listener?, themeSet: ThemeOverride.ThemeSet)
    : ThemeOverride.ThemeOverrideListener {

    override val isAlive = true

    private val themeOverride = ThemeOverride(themeSet, this)

    private var currentTheme = themeOverride.getTheme(base)
        set(value) {
            if (field != value) {
                field = value
                themedContext = ContextThemeWrapper(base, value)
                listener?.onThemeChanged()
            }
        }
    private var themedContext = ContextThemeWrapper(base, currentTheme)

    fun startListening() {
        ThemeManager.getInstance(base).addOverride(themeOverride)
    }

    fun stopListening() {
        ThemeManager.getInstance(base).removeOverride(themeOverride)
    }

    override fun reloadTheme() {
        currentTheme = themeOverride.getTheme(base)
    }

    override fun applyTheme(themeRes: Int) {
        currentTheme = themeOverride.getTheme(base)
    }

    fun get() = themedContext

    interface Listener {

        fun onThemeChanged()
    }
}
