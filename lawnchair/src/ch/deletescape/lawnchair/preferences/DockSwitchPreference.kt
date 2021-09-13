package ch.deletescape.lawnchair.preferences

import android.content.Context
import androidx.annotation.Keep
import android.util.AttributeSet
import android.view.View
import android.widget.Switch
import ch.deletescape.lawnchair.applyColor
import ch.deletescape.lawnchair.getColorEngineAccent
import ch.deletescape.lawnchair.lawnchairPrefs
import ch.deletescape.lawnchair.settings.ui.search.SearchIndex
import com.android.launcher3.Utilities
import kotlin.reflect.KMutableProperty1

class DockSwitchPreference(context: Context, attrs: AttributeSet? = null) : StyledSwitchPreferenceCompat(context, attrs) {

    private val prefs = Utilities.getLawnchairPrefs(context)
    private val currentStyle get() = prefs.dockStyles.currentStyle
    private val inverted get() = key == "enableGradient"

    @Suppress("UNCHECKED_CAST")
    private val property get() = DockStyle.properties[key] as? KMutableProperty1<DockStyle, Boolean>

    private val onChangeListener = { isChecked = getPersistedBoolean(false) }

    init {
        isChecked = getPersistedBoolean(false)
    }

    override fun onSetInitialValue(restoreValue: Boolean, defaultValue: Any?) {
        isChecked = getPersistedBoolean(false)
    }

    override fun onAttached() {
        super.onAttached()
        prefs.dockStyles.addListener(onChangeListener)
    }

    override fun onDetached() {
        super.onDetached()
        prefs.dockStyles.removeListener(onChangeListener)
    }

    override fun getPersistedBoolean(defaultReturnValue: Boolean): Boolean {
        if (inverted) return property?.get(currentStyle) != true
        return property?.get(currentStyle) == true
    }

    override fun persistBoolean(value: Boolean): Boolean {
        property?.set(currentStyle, if (inverted) !value else value)
        return property != null
    }

    class DockSwitchSlice(context: Context, attrs: AttributeSet) : SwitchSlice(context, attrs) {

        override fun createSliceView(): View {
            return DockSwitchSliceView(context, key)
        }
    }

    class DockSwitchSliceView(
            context: Context,
            private val key: String)
        : Switch(context) {

        private val currentStyle get() = context.lawnchairPrefs.dockStyles.currentStyle
        private val inverted get() = key == "enableGradient"

        @Suppress("UNCHECKED_CAST")
        private val property get() = DockStyle.properties[key] as? KMutableProperty1<DockStyle, Boolean>
        private val listener = ::onChange

        init {
            applyColor(context.getColorEngineAccent())
            setOnCheckedChangeListener { _, isChecked ->
                persistBoolean(isChecked)
            }
            isChecked = getPersistedBoolean()
        }

        override fun onAttachedToWindow() {
            super.onAttachedToWindow()
            context.lawnchairPrefs.dockStyles.addListener(listener)
        }

        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            context.lawnchairPrefs.dockStyles.removeListener(listener)
        }

        private fun onChange() {
            isChecked = getPersistedBoolean()
        }

        private fun getPersistedBoolean(): Boolean {
            if (inverted) return property?.get(currentStyle) != true
            return property?.get(currentStyle) == true
        }

        private fun persistBoolean(value: Boolean): Boolean {
            property?.set(currentStyle, if (inverted) !value else value)
            return property != null
        }
    }

    companion object {

        @Keep
        @JvmStatic
        val sliceProvider = SearchIndex.SliceProvider.fromLambda(::DockSwitchSlice)
    }
}
