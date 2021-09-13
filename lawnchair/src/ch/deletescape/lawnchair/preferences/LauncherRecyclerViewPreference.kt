

package ch.deletescape.lawnchair.preferences

import android.content.Context
import android.preference.DialogPreference
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import ch.deletescape.lawnchair.font.CustomFontManager
import ch.deletescape.lawnchair.setCustomFont
import ch.deletescape.lawnchair.theme.ThemeOverride
import com.android.launcher3.R

abstract class LauncherRecyclerViewPreference(context: Context, attrs: AttributeSet?) : DialogPreference(context, attrs) {

    private val themeRes = ThemeOverride.LauncherDialog().getTheme(context)
    protected val themedContext = ContextThemeWrapper(context, themeRes)

    init {
        dialogLayoutResource = R.layout.dialog_preference_recyclerview
    }

    override fun onBindDialogView(view: View) {
        super.onBindDialogView(view)
        onBindRecyclerView(view.findViewById(R.id.list))

        view.post {
            val window = dialog.window ?: return@post
            window.findViewById<TextView>(android.R.id.button1)?.setCustomFont(CustomFontManager.FONT_DIALOG_TITLE)
            window.findViewById<TextView>(android.R.id.button2)?.setCustomFont(CustomFontManager.FONT_DIALOG_TITLE)
            window.findViewById<TextView>(android.R.id.button3)?.setCustomFont(CustomFontManager.FONT_DIALOG_TITLE)
        }
    }

    abstract fun onBindRecyclerView(recyclerView: RecyclerView)
}
