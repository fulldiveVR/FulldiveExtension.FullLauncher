

package ch.deletescape.lawnchair.views

import android.content.Context
import android.util.AttributeSet
import ch.deletescape.lawnchair.settings.ui.DecorLayout

class PreferenceNestedScrollView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : InsettableNestedScrollView(context, attrs, defStyleAttr) {

    private var elevationHelper: DecorLayout.ToolbarElevationHelper? = null
        set(value) {
            field?.destroy()
            field = value
        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        elevationHelper = DecorLayout.ToolbarElevationHelper(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        elevationHelper = null
    }
}
