

package ch.deletescape.lawnchair.sesame.views

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.DialogTitle
import android.util.AttributeSet
import ch.deletescape.lawnchair.font.CustomFontManager
import ch.deletescape.lawnchair.setCustomFont

/**
 * Simple dialog title class to use in externally displayed layouts where we can't programmatically change the design otherwise
 */
@SuppressLint("RestrictedApi")
class LCDialogTitle(context: Context?, attrs: AttributeSet?) : DialogTitle(context, attrs) {
    init {
        setCustomFont(CustomFontManager.FONT_DIALOG_TITLE)
    }
}