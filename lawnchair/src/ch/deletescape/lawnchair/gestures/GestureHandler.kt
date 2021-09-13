

package ch.deletescape.lawnchair.gestures

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.View
import com.android.launcher3.R
import org.json.JSONObject

abstract class GestureHandler(val context: Context, val config: JSONObject?) {

    abstract val displayName: String
    open val requiresForeground: Boolean = false
    open val hasConfig = false
    open val configIntent: Intent? = null
    open val isAvailable: Boolean = true
    open val icon: Drawable? = null
    open val iconResource: Intent.ShortcutIconResource by lazy { Intent.ShortcutIconResource.fromContext(context, R.mipmap.ic_launcher) }

    abstract fun onGestureTrigger(controller: GestureController, view: View? = null)

    protected open fun saveConfig(config: JSONObject) {

    }

    open fun onConfigResult(data: Intent?) {

    }

    open fun onDestroy() {

    }

    open fun isAvailableForSwipeUp(isSwipeUp: Boolean) = isAvailable

    override fun toString(): String {
        return JSONObject().apply {
            put("class", this@GestureHandler::class.java.name)
            if (hasConfig) {
                val config = JSONObject()
                saveConfig(config)
                put("config", config)
            }
        }.toString()
    }
}

class BlankGestureHandler(context: Context, config: JSONObject?) : GestureHandler(context, config) {

    override val displayName: String = context.getString(R.string.action_none)

    override fun onGestureTrigger(controller: GestureController, view: View?) {

    }
}

class RunnableGestureHandler(context: Context,
        private val onTrigger: Runnable) : GestureHandler(context, null) {

    override val displayName: String = context.getString(R.string.action_none)

    override fun onGestureTrigger(controller: GestureController, view: View?) {
        onTrigger.run()
    }
}
