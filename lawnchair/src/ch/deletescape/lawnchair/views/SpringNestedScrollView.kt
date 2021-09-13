
package ch.deletescape.lawnchair.views

import android.content.Context
import android.graphics.Canvas
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_BOTTOM
import android.util.AttributeSet
import android.view.View
import ch.deletescape.lawnchair.colors.ColorEngine
import ch.deletescape.lawnchair.getColorAccent
import ch.deletescape.lawnchair.getColorAttr
import ch.deletescape.lawnchair.util.getField

open class SpringNestedScrollView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    private val springManager = SpringEdgeEffect.Manager(this)
    private val scrollBarColor by lazy {
        val colorControlNormal = context.getColorAttr(android.R.attr.colorControlNormal)
        val useAccentColor = colorControlNormal == context.getColorAccent()
        if (useAccentColor) ColorEngine.getInstance(context).accent else colorControlNormal
    }

    open var shouldTranslateSelf = true

    var isTopFadingEdgeEnabled = true

    init {
        getField<NestedScrollView>("mEdgeGlowTop").set(this, springManager.createEdgeEffect(DIRECTION_BOTTOM, true))
        getField<NestedScrollView>("mEdgeGlowBottom").set(this, springManager.createEdgeEffect(DIRECTION_BOTTOM))
        overScrollMode = View.OVER_SCROLL_ALWAYS
    }

    override fun draw(canvas: Canvas) {
        springManager.withSpring(canvas, shouldTranslateSelf) {
            super.draw(canvas)
            false
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        springManager.withSpring(canvas, !shouldTranslateSelf) {
            super.dispatchDraw(canvas)
            false
        }
    }

    override fun getTopFadingEdgeStrength(): Float {
        return if (isTopFadingEdgeEnabled) super.getTopFadingEdgeStrength() else 0f
    }
}
