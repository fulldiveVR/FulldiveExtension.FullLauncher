

package ch.deletescape.lawnchair.preferences

import android.content.Context
import androidx.preference.ListPreference
import android.util.AttributeSet
import ch.deletescape.lawnchair.animations.AnimationType
import ch.deletescape.lawnchair.util.buildEntries

import com.android.launcher3.R
import com.android.launcher3.Utilities

class AnimationTypePreference(context: Context, attrs: AttributeSet?) : ListPreference(context, attrs) {

    init {
        buildEntries {
            addEntry(R.string.animation_type_default, "")
            if (AnimationType.hasControlRemoteAppTransitionPermission(context)) {
                addEntry(R.string.animation_type_pie, AnimationType.TYPE_PIE)
            } else {
                addEntry(R.string.animation_type_pie_like, AnimationType.TYPE_PIE)
            }
            if (Utilities.ATLEAST_MARSHMALLOW) {
                addEntry(R.string.animation_type_reveal, AnimationType.TYPE_REVEAL)
            }
            addEntry(R.string.animation_type_slide_up, AnimationType.TYPE_SLIDE_UP)
            addEntry(R.string.animation_type_scale_up, AnimationType.TYPE_SCALE_UP)
            addEntry(R.string.animation_type_blink, AnimationType.TYPE_BLINK)
            addEntry(R.string.animation_type_fade, AnimationType.TYPE_FADE)
        }
    }
}
