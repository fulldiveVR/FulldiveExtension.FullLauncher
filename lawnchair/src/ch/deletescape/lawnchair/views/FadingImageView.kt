

package ch.deletescape.lawnchair.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet

class FadingImageView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val transparentDrawable = resources.getDrawable(android.R.color.transparent)

    var image: Drawable? = null
        set(value) {
            field = value
            if (value != null) {
                setImageDrawable(TransitionDrawable(arrayOf(transparentDrawable, value))
                        .apply { startTransition(125) })
            } else {
                setImageDrawable(null)
            }
        }
}
