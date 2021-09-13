

package ch.deletescape.lawnchair.preferences

import android.content.Context
import android.preference.SwitchPreference
//import android.support.v14.preference.SwitchPreference
import androidx.preference.AndroidResources
import android.util.AttributeSet
import android.view.View
import android.widget.Switch
import ch.deletescape.lawnchair.applyColor
import ch.deletescape.lawnchair.colors.ColorEngine


open class StyledSwitchPreference(context: Context, attrs: AttributeSet?) : SwitchPreference(context, attrs), ColorEngine.OnColorChangeListener {

    private var checkableView: View? = null

    override fun onBindView(view: View?) {
        super.onBindView(view)
        checkableView = view?.findViewById(AndroidResources.ANDROID_R_SWITCH_WIDGET)
        ColorEngine.getInstance(context).addColorChangeListeners(this, ColorEngine.Resolvers.ACCENT)
    }

    override fun onColorChange(resolveInfo: ColorEngine.ResolveInfo) {
        if (resolveInfo.key == ColorEngine.Resolvers.ACCENT && checkableView is Switch) {
            (checkableView as Switch).applyColor(resolveInfo.color)
        }
    }

    override fun onPrepareForRemoval() {
        super.onPrepareForRemoval()
        ColorEngine.getInstance(context).removeColorChangeListeners(this, ColorEngine.Resolvers.ACCENT)
    }
}
