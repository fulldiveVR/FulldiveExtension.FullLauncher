
package ch.deletescape.lawnchair.gestures.ui

import ch.deletescape.lawnchair.LawnchairLauncher
import ch.deletescape.lawnchair.gestures.GestureHandler
import com.android.launcher3.Launcher
import com.android.launcher3.states.InternalStateHandler

class GestureHandlerInitListener(private val handler: GestureHandler) : InternalStateHandler() {

    override fun init(launcher: Launcher, alreadyOnHome: Boolean): Boolean {
        handler.onGestureTrigger((launcher as LawnchairLauncher).gestureController)
        return true
    }
}
