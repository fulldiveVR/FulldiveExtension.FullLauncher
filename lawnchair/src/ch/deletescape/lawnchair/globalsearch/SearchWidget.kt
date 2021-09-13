

package ch.deletescape.lawnchair.globalsearch

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.google.android.apps.nexuslauncher.qsb.HotseatQsbWidget

class SearchWidget(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    override fun addView(child: View?) {
        super.addView(child)
        if (child is HotseatQsbWidget) {
            child.setWidgetMode(true)
        }
    }
}